package com.drinkscabinet.aoc2020

private fun main() {
    val recipes = input.lines().map { Recipe.parse(it) }.toSet()

    recipes.forEach { println(it) }

    // get all the allergens and ingredients out
    val allergenIndex = mutableMapOf<String, MutableSet<Recipe>>()
    recipes.forEach { recipe ->
        recipe.allergens.forEach {
            allergenIndex.computeIfAbsent(it) { mutableSetOf() }.add(recipe)
        }
    }

    allergenIndex.forEach { println(it) }

    val allergenToIngredient = mutableMapOf<String, String>()
    val ingredientsToMap = recipes.flatMap { it.ingredients }.toMutableSet()
    val allergensToMap = recipes.flatMap { it.allergens }.toMutableSet()

    while (allergensToMap.isNotEmpty()) {
        val candidates = mutableMapOf<String, Set<String>>()
        for (allergen in allergensToMap) {
            // Find all the candidates for this by intersecting the recipes, and the remaining ingredients
            val recipesForAllergen = allergenIndex[allergen]!!
            candidates[allergen] = recipesForAllergen.map { it.ingredients }
                .fold(ingredientsToMap, { a, b -> a.intersect(b).toMutableSet() })
        }
        candidates.forEach { println("Allergen ${it.key} has ${it.value.size} candidates") }
        // Find any that have just 1 answer and map them
        candidates.filter { it.value.size == 1 }.forEach {
            val ingredient = it.value.first()
            val allergen = it.key
            allergenToIngredient[allergen] = ingredient
            println("Mapped $allergen to $ingredient")
            ingredientsToMap.remove(ingredient)
            allergensToMap.remove(allergen)
        }
    }
    println(allergensToMap)
    println("Remaining ingredients: $ingredientsToMap")
    // now count occurrences
    val occurrences = recipes.flatMap { it.ingredients }.count { ingredientsToMap.contains(it) }
    println("Part1=$occurrences")

    println(allergenToIngredient.keys.sorted().joinToString(","))
    println("Part2")
}


private data class Recipe(val ingredients: Set<String>, val allergens: Set<String>) {
    companion object {
        fun parse(s: String): Recipe {
            val ingredients = s.substringBefore(" (")
            if (ingredients.isEmpty()) throw IllegalArgumentException("Missing allergens in $s")
            val ingSet = ingredients.split(" ").toSet()

            val allergens = s.substringAfter("(contains ").substringBefore(")")
            val allSet = allergens.split(",").map { it.trim() }.toSet()
            return Recipe(ingSet, allSet)
        }
    }
}


private val testInput = """
    mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
    trh fvjkl sbzzf mxmxvkd (contains dairy)
    sqjhc fvjkl (contains soy)
    sqjhc mxmxvkd sbzzf (contains fish)
""".trimIndent()


private val input = """
    fngc bhnfx ssfnt znrzgs fcdf bgmh pgblcd lmfrl hrcfc bbfxk dhfng xppbd ntggc fhdx kfkjg tzsx xpfhcf jmbpvd qchmh svfbm zmjjbr jjhl pcqd xgfgx ghlzj jxrnq ncgqfdk fthkdz kmdrkg cnxtjz lfpc lvbpvc tzxdf bgx pvkrf txdjzsk dzgljz snxzjb xjd zsmmf jdvxzv crbdxfx ckspdd kmnmh dstct vhbqgc nqbnmzx rhzdcb vvv jmhsl mrfqdkt phzm lqjfs pgpgvs (contains shellfish, soy)
    rxlps pnddb lvbpvc pgblcd nqbnmzx ztd fzlffdz qfqskr dhfng dhphkz sbjzc kghc xbgz vtcs xdhc crbdxfx vmsksq thzkkk kmnmh lljxf pgrc jhxv xpfhcf vbdgvk dzgljz jxrnq ckxdjf kdkm jbtf dnxdd vhbqgc nffxvs hgxz nmbdn ntggc ppcqfhmd bgmh jptf lrj vjdvrf brsqck fnll lbhzxvf ztc lfpc kpd svfbm dxclx bfrhb nmmv tzsx qctchfv pgpgvs tlsd mgvs znrzgs rhzdcb jjhl bnd nt dstct ghlzj stndbt (contains soy)
    vmsksq jjrsrf dfdkm xjd kpd skzz bmvfs fgxdq kmtmlx fzlffdz zlgfs zfc tmlz ndmrz dstct ddpc ksvfdjt dxclx jsgkn ghlzj dgsbm fszjvn dqjjt dqcg zmjjbr hrcfc cbxp dzgljz pgblcd txdcjdc ckspdd fsmz rsrncv zbjnr ckxdjf ntggc dhfng nmbdn brsqck pkzs tzxdf tfvcrd znrzgs vsn brdxmlfl bclml nntlxx dhphkz ttvvp kfkjg zbblv gdmj xppbd nqbnmzx lhsl bqlk shjxf fvz mhtrd (contains shellfish, eggs)
    cnxtjz dhfng zszg bgx dstct jdvxzv vbdgvk dfdkm xhkdc vjdvrf zjkc nfh ndmrz ztc nffxvs nqbnmzx mrfqdkt pgblcd gdmj ntggc kvljbg znrzgs cbsqvgt tlsd vmsksq gsn hpvmrm brsqck fsmz nmmv kmdrkg ppcqfhmd crqhxx bhnfx xjd stndbt qfqskr hqj lktt lhvv kfkjg (contains soy, eggs, sesame)
    kmnmh zfc xdhc lfglf tzsx txdcjdc vhbqgc nfh vmsksq rbpxvk qnhck dhfng ggjh pgblcd jhxv ntggc fthkdz ksvfdjt nqbnmzx xppbd bhmvf dstct rpbv ztc tfvcrd kjzb lqjfs nmbdn mhtrd tmsllr ckxdjf jlgr zjkc rrhg gsn jmhsl kdkm shjxf dgsbm pcqd zmjjbr hgxz pgpgvs bjp snxzjb jlls crbdxfx qfqskr ghlzj znrzgs gffvc xxczt lktt ncgqfdk zszg jsgkn kvljbg sbjzc bmvfs fnll rxlps tmlz pnddb zlgfs (contains soy, eggs, sesame)
    nzdttq kpkv fthkdz dzgljz znrzgs pcqd pgpgvs lktt dhfng tjrj xhkdc cnxtjz hgxz rxlps fvz rkvq tsnr cbxp fcdf lfglf mhtrd qqtpvf fgxdq snxzjb dth gsn zsmmf ksvfdjt tlsd bclml vvv vsn qctchfv kmnmh dstct hqj rhzdcb dqcg ntggc nntlxx cd ddpc stndbt bgmh tmsllr lhsl mxrp ggjh jjhl svfbm vmsksq ghlzj txdjzsk zfc bnd dnxdd vpj nmmv jdvxzv zjkc bhmvf thzkkk jjrsrf zvgdp nqbnmzx kvljbg vbdgvk jzdqp brdxmlfl pvkrf bbfxk lljxf zmjjbr dfhtlrg mrfqdkt (contains fish)
    nntlxx pgblcd lfglf tzxdf mxrp ntggc tfvcrd jxrnq ncgqfdk pmlpk xpfhcf dnxdd fvz jlls rsrncv xhkdc qnhck jjhl kmdrkg nmmv dstct nqbnmzx bgmh zmjjbr qqtpvf xjd dhfng bnhhr bmvfs kghc tsnr lhvv xdhc ndmrz xppbd crbdxfx pnddb qfqskr bgx lhsl zgthq ghlzj kdkm sdxhbf xbgz cnxtjz ksvfdjt gsn dqjjt rbpxvk jlgr nnqnj pgpgvs lgkbt bclml (contains dairy)
    nmmv kfkjg lqjfs pcqd rsrncv qlf cbxp dstct ntggc tlsd fbmjp dth ddpc nzdttq txdjzsk tsnr kjzb rpbv jxrnq xgfgx fzlffdz mxrp cd kmnmh rbpxvk zjkc mhtrd vhbqgc dhfng qqtpvf nqbnmzx bhnfx gdmj znrzgs nmbdn qctchfv bgmh pgblcd fthkdz kdkm pvkrf jlls pnddb mrfqdkt jmhsl ghlzj jlgr jmbpvd xppbd sbjzc (contains soy, wheat)
    qlf fngc mgvs dstct rbpxvk qctchfv rmqvb rcznlx vmsksq xhkdc pgpgvs lljxf zvgdp ncgqfdk jsgkn jhxv qfqskr kmdrkg dnxdd jbtf jlgr zgthq sdvnc pnddb pgblcd txdcjdc nqbnmzx bgmh nfh ckxdjf vgqftvk jjrsrf cd txdjzsk tfvcrd kvljbg lrj znrzgs dhfng pcqd zfc bqlk ghlzj (contains peanuts, eggs)
    pvkrf xgfgx scdgmh rpbv ntggc xpfhcf snxzjb dqcg lljxf ckxdjf fsmz dstct hrcfc mrfqdkt fgxdq xppbd dqjjt txdcjdc znrzgs dfdkm bgmh dzgljz rrhg nqbnmzx cbsqvgt thzkkk zvgdp jjrsrf pgblcd vjdvrf tmlz sdxhbf fvz kmdrkg xhkdc zsmmf vbdgvk tzsx dhphkz kvljbg sbjzc xcnkt dhfng kdkm lqjfs pcqd kmnmh pmlpk bhmvf (contains sesame, dairy, eggs)
    cnxtjz pgblcd qqtpvf pvkrf zmjjbr skzz ghlzj kpkv tmlz zbblv ntggc qnhck kjzb pcqd sdvnc lhvv fzlffdz ztc xxczt cd fvz znrzgs mxrp rmqvb tzsx jmhsl vtcs bfrhb lvbpvc ckxdjf bjp vvv dhfng nqbnmzx sbjzc pnddb rkvq bnd tjrj pgpgvs jjhl zbjnr scdgmh bhnfx dstct skhp bqlk fgxdq cbxp thzkkk (contains eggs, dairy)
    mrfqdkt crbdxfx pzvddz sbjzc kmdrkg lbhzxvf hqnm ntggc fvz rhzdcb rxlps tfvcrd dhfng lfpc ztd pmlpk txdjzsk xhkdc bjp nntlxx shjxf sdxhbf fcdf snxzjb hqj jmbpvd fsmz kghc mfqqh nmbdn pgblcd dstct ckxdjf zgthq lktt skhp fthkdz scdgmh bbfxk brdxmlfl nqbnmzx fhdx pcqd ggjh kjzb zvgdp jjhl lfglf qctchfv blx dhphkz znrzgs xpfhcf stndbt tmlz (contains shellfish)
    ndmrz mxrp lfglf nqbnmzx dhfng pgpgvs lrj kjzb vcvcrxx cczqczv lhsl fcdf ncgqfdk hqj dxclx rcznlx bhmvf fgxdq ntggc jhxv scdgmh pkzs fszjvn jjrsrf zmjjbr pgrc xjd kghc xhkdc vsn dstct ghlzj dktxc ztc pcqd pgblcd nnqnj nzdttq (contains sesame, peanuts)
    bfrhb vsn bclml jqpc lrj bnhhr cbxp lfpc bjp pgblcd nqbnmzx dhfng vgqftvk bgx vbdgvk ksvfdjt jxrnq ztd xcnkt znrzgs dqcg vpj sdxhbf bqlk lfglf xjd kjzb lktt pvkrf lhvv jzdqp qlf ddpc rxlps zbblv hqnm brsqck kghc dknxl bhmvf zlgfs cd kpkv crbdxfx jmhsl sts tzsx xdhc ggjh shjxf ssfnt bbfxk kdvq hqj ghlzj hrcfc dstct bgmh xhkdc nnqnj jbtf tmlz zgthq (contains sesame)
    kvljbg kjzb hqj xdhc ssfnt ckxdjf mhtrd rkvq bgmh pgpgvs bqlk ncgqfdk qchmh mxrp lhvv bclml jjhl kfkjg pvkrf tmlz kghc ghlzj znrzgs rrhg zbjnr qnhck dknxl crqhxx kmnmh jjrsrf bbfxk jlgr lfpc scdgmh dhfng kdvq xcnkt cbxp gsn ndmrz vcvcrxx xpfhcf xhkdc lfglf hqnm bhnfx ntggc dstct lvbpvc rsrncv nnqnj mfqqh qqtpvf lgkbt zsmmf nqbnmzx cczqczv (contains fish, soy)
    nqbnmzx dth ndmrz ncgqfdk xhkdc pgrc ntggc zmjjbr fnll nmmv dqjjt jdvxzv lmfrl skzz txdjzsk bjp lbhzxvf rrhg kpkv pnddb vcvcrxx kmtmlx pgblcd bfrhb vmsksq crbdxfx jlgr xxczt dgsbm snxzjb lqjfs vvv dktxc dhfng xppbd xpfhcf bhnfx qfqskr scdgmh lvbpvc fgxdq kmnmh vhbqgc svfbm zbblv zsmmf ssfnt nfh dstct pzvddz rcznlx zvgdp sbjzc ggjh znrzgs skhp fszjvn brdxmlfl mgvs tzxdf cczqczv rbpxvk vjdvrf nmbdn (contains wheat)
    vhbqgc vcvcrxx tmlz dth znrzgs bhnfx brdxmlfl nqbnmzx xhkdc vtcs lqjfs bnhhr ncgqfdk bmvfs ghlzj vgqftvk rsxpjt xgfgx vvv ntggc dstct vsn dfdkm rcznlx jqpc pgblcd zszg jdvxzv zlgfs blx cd lmfrl zbjnr jbtf shjxf kghc fthkdz lhvv lgkbt crqhxx cczqczv hpvmrm nzdttq dxclx jzdqp qnhck rhzdcb kfkjg ztd pgpgvs hqj lfpc jmbpvd bvnbp mfqqh bbfxk ssfnt pvkrf bqlk fbmjp (contains eggs)
    ddpc tlsd rkvq rpbv hqnm shjxf dth bhnfx pmlpk sdvnc zsmmf znrzgs kjzb jhxv vcvcrxx rsrncv dqcg jxrnq lfpc mhtrd fthkdz pzvddz ztc nfh cbxp tspqs svfbm mgvs bqlk fvz hqj hrcfc vbdgvk ckspdd fbmjp fsmz sts txdjzsk rsxpjt tzxdf cbsqvgt jmhsl pgblcd txdcjdc skhp lhsl xdhc dhfng zmjjbr gffvc vgqftvk zszg rhzdcb jptf dstct stndbt rbpxvk ntggc ksvfdjt xhkdc lktt snxzjb zvgdp nqbnmzx cd bhmvf jjrsrf pkzs kpd dfdkm bnhhr bgmh qlf ttvvp zgthq vmsksq xgfgx jzdqp fhdx brsqck vjdvrf sbjzc cczqczv jqpc kdvq jbtf (contains soy, sesame)
    bmvfs dfdkm pvkrf jlls cnxtjz thzkkk hgxz zsmmf blx jhxv jjrsrf vsn lfglf cbxp ghlzj nntlxx zbblv fcdf jxrnq mhtrd lljxf dhfng xxczt fthkdz lmfrl kmdrkg lvbpvc vpj jsgkn pgpgvs skhp dknxl kpd bhnfx nt rsrncv lqjfs fgxdq ppcqfhmd bclml xhkdc dstct ztc fbmjp dzgljz nqbnmzx ntggc zmjjbr bjp dktxc dxclx mxrp hrcfc zvgdp ncgqfdk tjrj sdxhbf xgfgx lhvv bnhhr ckxdjf bqlk tfvcrd jzdqp bgx znrzgs lhsl fhdx crbdxfx vcvcrxx bnd nffxvs (contains peanuts, dairy)
    jbtf skhp lmfrl nfh mxrp pgblcd tmlz ppcqfhmd dktxc sdvnc kvljbg bjp txdjzsk lhsl rsrncv pgrc jjrsrf mrfqdkt dxclx dstct jdvxzv fgxdq bhnfx lgkbt fcdf xpfhcf nnqnj crbdxfx dhfng tspqs crqhxx zfc qlf hpvmrm nzdttq lfpc vsn pnddb vvv xhkdc jlls mgvs lbhzxvf sts lfglf jjhl qctchfv dth cczqczv cnxtjz tzxdf brdxmlfl ghlzj ksvfdjt znrzgs vcvcrxx ztd txdcjdc cbsqvgt nqbnmzx (contains dairy, wheat)
    pmlpk dxclx bgmh snxzjb nfh ckxdjf kghc gffvc hrcfc kjzb dgsbm kpd kmnmh lqjfs xcnkt ntggc jmhsl dfhtlrg vtcs zvgdp tzsx dhfng tmlz xhkdc ztc nzdttq ncgqfdk bbfxk jptf zlgfs dktxc vjdvrf bfrhb phzm hpvmrm rxlps kdkm kdvq kfkjg pgblcd pcqd xdhc xbgz dhphkz sdxhbf nqbnmzx znrzgs nnqnj xjd ghlzj dknxl zsmmf vsn blx gdmj bmvfs sbjzc rrhg rmqvb tsnr pnddb cd pgrc jsgkn xpfhcf zjkc jlls bhnfx vhbqgc bgx lgkbt txdjzsk qqtpvf (contains soy)
    lmfrl crbdxfx kmdrkg dgsbm jptf jbtf nqbnmzx phzm ntggc hqj qctchfv nt pgblcd brsqck mfqqh ztc lqjfs dfhtlrg dstct cczqczv svfbm nffxvs bfrhb dhfng bbfxk sbjzc tmlz pvkrf lgkbt fhdx pmlpk jsgkn bnhhr lvbpvc tzxdf jhxv bclml tlsd ssfnt dxclx scdgmh fthkdz zvgdp fzlffdz fcdf lhvv tjrj xxczt jmbpvd hrcfc xpfhcf xhkdc ddpc kdkm mgvs xdhc fvz rcznlx mhtrd fngc fsmz sdxhbf vvv xppbd vgqftvk thzkkk zjkc cd vtcs znrzgs (contains eggs)
    ppcqfhmd gdmj zgthq dfdkm nqbnmzx dhfng jzdqp zvgdp hpvmrm dth bhnfx skzz lktt xxczt kmdrkg lvbpvc xpfhcf brsqck dzgljz lhvv jhxv jqpc ghlzj zszg lbhzxvf fngc fhdx tsnr nmmv brdxmlfl mxrp kghc nzdttq pgblcd jmbpvd qfqskr kmnmh ndmrz dstct vvv xbgz nntlxx rxlps rhzdcb bmvfs xhkdc hqnm zsmmf sbjzc hqj tzsx jlls nt ztc znrzgs bnhhr thzkkk bclml (contains eggs)
    crqhxx jsgkn nt jqpc bgx cnxtjz ppcqfhmd lhvv nffxvs dktxc nmbdn xhkdc txdcjdc bfrhb znrzgs brdxmlfl rsrncv lqjfs ncgqfdk ddpc dfdkm pgblcd mgvs pzvddz lfglf ndmrz hpvmrm mhtrd kfkjg rrhg hqnm dhfng sdxhbf sts thzkkk hgxz ntggc zgthq fszjvn zfc scdgmh pvkrf jjrsrf xxczt ghlzj gffvc jmhsl jlls tjrj ggjh nqbnmzx sdvnc tmlz jmbpvd tspqs bclml svfbm phzm fzlffdz jbtf cbsqvgt tsnr nntlxx tzxdf fnll lgkbt (contains fish, dairy)
    bfrhb lvbpvc brdxmlfl ssfnt kpkv scdgmh lktt vmsksq gsn fthkdz qfqskr jxrnq fzlffdz nmbdn xppbd jbtf ppcqfhmd rbpxvk dstct nmmv jptf znrzgs tspqs pnddb ntggc nt fhdx dgsbm tzsx rkvq nqbnmzx rxlps ghlzj dhfng xpfhcf pgblcd dktxc zbjnr kmdrkg lfpc lhvv vbdgvk lrj phzm (contains soy)
    kvljbg kfkjg zmjjbr txdcjdc thzkkk bhmvf ppcqfhmd pgpgvs tjrj kmnmh pvkrf xcnkt cbxp dstct mhtrd dknxl ggjh kmdrkg fngc dfdkm pnddb crbdxfx gsn nqbnmzx fbmjp hqj ztd rsrncv mfqqh hqnm rpbv kpkv vmsksq zvgdp ddpc pmlpk snxzjb ghlzj ckspdd nffxvs hrcfc xhkdc fszjvn bfrhb xxczt cbsqvgt dhfng nmmv xdhc pgblcd jhxv lqjfs tzsx hgxz bgmh dhphkz znrzgs bnhhr rxlps (contains wheat, peanuts)
    rmqvb dstct ghlzj cczqczv pgrc ckspdd phzm hqj vsn dth jptf ncgqfdk tmlz dxclx zlgfs nqbnmzx pcqd vpj jjhl ztd mfqqh pkzs xppbd xxczt rrhg bhnfx vbdgvk bmvfs lfglf qqtpvf dknxl pvkrf jqpc shjxf hrcfc pnddb ntggc vgqftvk pgblcd zgthq lfpc dfhtlrg qnhck xhkdc txdcjdc dhfng jmhsl jsgkn lgkbt bgmh sdvnc rsrncv kvljbg pmlpk rpbv nntlxx ggjh (contains sesame, eggs)
    rsrncv xhkdc tspqs pcqd zgthq rrhg nmbdn fcdf skzz kpkv pkzs dxclx pgpgvs dktxc hrcfc ndmrz tzsx xcnkt brsqck zvgdp jlgr hqj nt xgfgx bjp rxlps tsnr tfvcrd svfbm znrzgs jmbpvd fngc pgblcd xdhc zsmmf jdvxzv lqjfs kdvq bvnbp nqbnmzx cbsqvgt dhfng ghlzj qchmh mfqqh kjzb vcvcrxx dstct ddpc crbdxfx tzxdf kmtmlx lgkbt dgsbm (contains dairy)
    dknxl xhkdc ttvvp dth kpkv dxclx pgblcd xpfhcf lqjfs nmbdn kmnmh nntlxx hpvmrm fzlffdz bgmh kmtmlx rxlps lljxf dnxdd gffvc pvkrf pmlpk dktxc mhtrd kpd pcqd vhbqgc jsgkn zsmmf gdmj tzsx kdvq ssfnt vjdvrf fvz phzm bclml fngc bfrhb ksvfdjt txdcjdc fszjvn xgfgx ghlzj vcvcrxx hqnm skhp tfvcrd jbtf zlgfs rpbv dzgljz rsrncv nmmv pgrc ppcqfhmd qlf mrfqdkt zbjnr nqbnmzx fsmz zfc lktt sbjzc fbmjp ntggc znrzgs jzdqp kdkm xjd tzxdf crqhxx sdvnc tmlz xbgz jjrsrf lhvv dhfng jhxv dhphkz xxczt qnhck xcnkt ztd lmfrl dqjjt vgqftvk (contains eggs, wheat, shellfish)
    brsqck ssfnt pcqd mhtrd ppcqfhmd fbmjp bbfxk gffvc pkzs shjxf lvbpvc nqbnmzx jxrnq dhfng jhxv jdvxzv mgvs rpbv ghlzj txdcjdc vhbqgc qchmh pgblcd sdvnc dgsbm bvnbp zvgdp lhsl dqjjt vpj zlgfs bnhhr zszg hqj kpkv nntlxx xppbd kdvq sdxhbf tspqs tmlz xhkdc pmlpk ztd znrzgs rsxpjt blx fsmz kmdrkg lljxf thzkkk rhzdcb skzz rxlps ntggc nffxvs lfglf rbpxvk hpvmrm kjzb nzdttq qqtpvf (contains soy, wheat, fish)
    lgkbt ggjh vtcs skzz lfpc pvkrf skhp dzgljz rbpxvk ghlzj txdcjdc xhkdc ckspdd bgmh qnhck zmjjbr fgxdq gsn dgsbm fzlffdz dth mrfqdkt xcnkt rrhg dxclx pmlpk nt nqbnmzx dfhtlrg kjzb rhzdcb cbsqvgt bvnbp vhbqgc ttvvp nmmv qlf zbblv vcvcrxx ppcqfhmd ckxdjf jzdqp lljxf pkzs xpfhcf rcznlx kdvq znrzgs nnqnj pzvddz rpbv dhfng qqtpvf vvv phzm kvljbg jptf fsmz pgblcd kmdrkg xbgz zgthq fszjvn bgx kmtmlx ssfnt pnddb mhtrd tzsx snxzjb pgpgvs tzxdf crbdxfx dstct (contains dairy)
    nnqnj lhsl sbjzc nqbnmzx gsn xjd jjrsrf bhnfx ztd bgmh fsmz dstct dgsbm crbdxfx tmsllr lmfrl kpkv skzz mhtrd dfdkm fbmjp nmmv ckxdjf bbfxk bfrhb jmhsl zsmmf xhkdc lfpc jmbpvd bvnbp lrj zlgfs lktt fgxdq znrzgs snxzjb sdxhbf xppbd vpj nmbdn tzxdf dknxl hqj zjkc rhzdcb pgblcd brsqck lvbpvc ckspdd gdmj xbgz vgqftvk ntggc zbjnr brdxmlfl cbsqvgt ddpc fnll vsn kdvq bhmvf kpd ghlzj jqpc kvljbg xgfgx (contains soy)
    gsn dstct vvv txdjzsk dhphkz xbgz qnhck tspqs ttvvp lhsl fzlffdz zmjjbr bclml dhfng bqlk kpd pgrc blx znrzgs qqtpvf nqbnmzx zsmmf rmqvb jdvxzv dfhtlrg nmmv lljxf cnxtjz bvnbp sdxhbf mrfqdkt lrj tfvcrd rxlps vmsksq zfc phzm xhkdc ghlzj xjd dzgljz xdhc tmlz dnxdd hgxz jsgkn ztc tjrj fcdf ntggc jhxv thzkkk jjrsrf fnll vbdgvk bgx jlgr ckxdjf vjdvrf vsn zjkc cbxp stndbt (contains peanuts, wheat)
    zsmmf vgqftvk stndbt pgrc nntlxx rsrncv fzlffdz vhbqgc bmvfs brdxmlfl tmsllr nmmv nmbdn rhzdcb fgxdq ntggc nzdttq thzkkk pgblcd dnxdd xdhc xxczt sts jjrsrf bqlk nffxvs bnd ckspdd bclml phzm ghlzj sdvnc qctchfv vsn jzdqp cbsqvgt dhfng cczqczv mhtrd mrfqdkt bfrhb dstct dth zfc nqbnmzx jhxv vvv znrzgs bhnfx nfh dktxc vpj xpfhcf fbmjp pgpgvs zgthq (contains wheat)
    ghlzj xbgz jdvxzv fthkdz cbxp pgblcd hpvmrm tzsx lqjfs gsn ppcqfhmd shjxf bvnbp vbdgvk zgthq bhnfx lvbpvc dknxl nqbnmzx dstct xhkdc rsrncv zsmmf dxclx znrzgs dfdkm jqpc thzkkk hgxz vpj ntggc hqj sts vvv nmmv tmlz txdjzsk mfqqh lmfrl snxzjb skhp (contains fish)
    rkvq jhxv pnddb ncgqfdk lfpc kmdrkg dfhtlrg snxzjb lhvv nqbnmzx lqjfs lljxf kghc znrzgs qlf hqj gsn mxrp jbtf bnhhr dstct pkzs pzvddz xxczt vtcs scdgmh vbdgvk kjzb ckxdjf qnhck tfvcrd nt jsgkn vpj rmqvb ntggc nnqnj mgvs zvgdp sts zjkc fhdx dhfng kpd hrcfc ztc dnxdd pmlpk lfglf rsrncv zgthq ksvfdjt jzdqp xhkdc bbfxk bclml zszg kmtmlx qchmh ggjh kvljbg dzgljz jdvxzv dth rpbv ppcqfhmd cnxtjz hpvmrm bgx pgblcd thzkkk crbdxfx xdhc ddpc jjrsrf vjdvrf jptf xcnkt dhphkz dknxl mrfqdkt dxclx jxrnq fvz vcvcrxx fcdf kdvq (contains dairy, shellfish)
    kmtmlx cnxtjz dktxc jzdqp jxrnq jmbpvd mrfqdkt lrj vpj lhvv rsxpjt txdcjdc pgblcd fzlffdz lktt kfkjg lfglf rpbv lbhzxvf dhphkz xpfhcf zlgfs tmsllr bhnfx gffvc bbfxk jhxv lljxf zmjjbr dqjjt jsgkn zbblv nqbnmzx jmhsl jdvxzv hqj dqcg zszg lqjfs blx bjp vmsksq rsrncv kjzb ckspdd sts xxczt ndmrz xhkdc fnll ddpc sdvnc dstct pcqd nt gdmj fgxdq jjhl pgpgvs xbgz jbtf lmfrl pkzs svfbm rcznlx ntggc dhfng tzsx mfqqh dfdkm thzkkk ssfnt dgsbm kpkv rhzdcb snxzjb znrzgs fszjvn qchmh fcdf scdgmh dknxl tsnr skhp fbmjp (contains fish, soy, dairy)
    xhkdc kvljbg pkzs fnll kjzb ckxdjf snxzjb kdkm ghlzj hpvmrm vmsksq lvbpvc lfpc qqtpvf bhmvf nmbdn zbblv dqjjt skzz nqbnmzx nt vhbqgc tspqs lktt kfkjg ckspdd dnxdd ntggc ttvvp lqjfs dstct dfdkm bclml thzkkk fhdx fcdf ndmrz rrhg vcvcrxx jsgkn cnxtjz qfqskr dhfng dktxc bbfxk bnhhr nffxvs tzxdf vpj pnddb phzm jlgr bnd mhtrd znrzgs (contains dairy, peanuts, fish)
    tspqs ghlzj dzgljz xpfhcf rmqvb tsnr fszjvn jlgr kjzb ddpc jbtf rsxpjt lhvv zvgdp tfvcrd qqtpvf snxzjb mxrp pmlpk znrzgs cbxp xhkdc lktt ntggc vpj kfkjg lljxf vjdvrf vsn fvz jdvxzv tzsx lgkbt rpbv dhfng nnqnj pnddb qlf ggjh sbjzc nfh ssfnt mrfqdkt skhp dhphkz jlls zbblv dktxc dknxl cd dstct ppcqfhmd bqlk bvnbp zgthq pgrc svfbm kdkm nqbnmzx (contains shellfish, dairy)
    xjd ndmrz vhbqgc blx dstct ghlzj ggjh zvgdp kpkv pzvddz hgxz tzxdf znrzgs pkzs tmlz rpbv dfhtlrg kvljbg bnhhr stndbt jmhsl cczqczv pgrc crbdxfx nfh ckxdjf bfrhb skhp rmqvb pgblcd cbxp xhkdc zsmmf jjrsrf lfpc lqjfs nzdttq brsqck jjhl jsgkn brdxmlfl dzgljz fzlffdz zjkc ssfnt jdvxzv fgxdq qnhck xbgz mfqqh bnd hqnm tzsx dhfng hqj mxrp vgqftvk vsn xpfhcf jzdqp dqcg jptf hrcfc dgsbm bgmh dhphkz hpvmrm fszjvn bgx scdgmh jlgr tlsd qlf lljxf ntggc cnxtjz kfkjg lhsl vpj ddpc bclml (contains wheat, dairy)
    cd kvljbg ndmrz qlf rsxpjt bgmh rpbv lljxf bhnfx dth mgvs xhkdc pnddb ghlzj xppbd jdvxzv ddpc qfqskr nmbdn crqhxx bmvfs dhfng xjd lhvv lrj dstct kghc kpkv pcqd znrzgs fnll tspqs bfrhb xbgz hpvmrm nqbnmzx zszg hrcfc gsn tmsllr tfvcrd phzm dknxl zmjjbr brdxmlfl pzvddz jmhsl rbpxvk xpfhcf lfpc xgfgx lbhzxvf pgblcd vgqftvk fbmjp bqlk lgkbt tlsd sdxhbf sts (contains shellfish, peanuts)
    zmjjbr xbgz cd zgthq qlf rpbv fcdf hqj lljxf ckxdjf crbdxfx jmhsl bclml dth dqcg bfrhb dqjjt ntggc tmsllr skhp lvbpvc snxzjb rbpxvk tzxdf nnqnj ksvfdjt ckspdd tfvcrd jqpc bgx dstct gffvc fthkdz hpvmrm hrcfc jzdqp lktt zsmmf cczqczv pvkrf kvljbg nt stndbt jmbpvd jdvxzv jlgr bhmvf mfqqh qchmh fnll blx hgxz bjp rrhg lqjfs nqbnmzx ghlzj sts vmsksq znrzgs kpd dgsbm pgblcd pnddb dhfng gdmj lfglf tsnr ggjh fbmjp xdhc lmfrl rmqvb dxclx qfqskr vtcs crqhxx vvv zlgfs lrj (contains sesame, wheat)
    sts fsmz vpj znrzgs jzdqp fszjvn fngc zszg mrfqdkt ncgqfdk nqbnmzx kdkm qnhck ntggc vmsksq jqpc pcqd fbmjp kfkjg bclml jsgkn dth qfqskr tsnr nntlxx jptf kghc kmdrkg ghlzj cbsqvgt fvz fnll hrcfc dktxc jmhsl xhkdc bmvfs sdvnc dhfng dknxl ddpc vvv kdvq vsn ttvvp bhmvf txdjzsk lljxf zlgfs tzsx rxlps vhbqgc stndbt bgx cbxp jmbpvd bnhhr tlsd phzm cd rcznlx crqhxx ksvfdjt zvgdp zfc dhphkz gdmj lhvv lrj rrhg cczqczv zbjnr xppbd dstct rbpxvk snxzjb scdgmh zbblv (contains wheat, fish, soy)
    bmvfs kghc hrcfc bhnfx kmtmlx zfc xgfgx nqbnmzx cbsqvgt lbhzxvf tzxdf ssfnt svfbm rmqvb bbfxk hqnm hpvmrm vvv qlf fsmz tmsllr cbxp gffvc dxclx lrj lmfrl dstct crbdxfx hgxz rhzdcb bgmh kdkm kpd tfvcrd cczqczv vgqftvk pgblcd kdvq sts bnd cd qchmh jjhl lqjfs dhfng vbdgvk nt zgthq xhkdc jjrsrf ghlzj pgpgvs bqlk zszg pzvddz sdxhbf lljxf fnll jmhsl zmjjbr lvbpvc crqhxx pkzs znrzgs jmbpvd jhxv kjzb snxzjb tzsx pnddb (contains peanuts)
""".trimIndent()