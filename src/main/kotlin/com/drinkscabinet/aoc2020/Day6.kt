package com.drinkscabinet.aoc2020

import com.drinkscabinet.chunks

private fun main() {
    val acc = input.chunks().map { it.chars().filter { it in 'a'.code..'z'.code }.distinct().count() }.sum()
    println("Part1=$acc")
    // 6911

    val acc2 = input.chunks().map { intersect2(it) }.sum()
    println("Part2=$acc2")
    // 4589 too high
    // 3473 correct
}

private fun intersect2(s: String): Int {
    return s.lines().map { it.chars().toArray().toSet() }.reduce { a, b -> a.intersect(b) }.size
}

private val testInput = """
    abc

    a
    b
    c

    ab
    ac

    a
    a
    a
    a

    b
""".trimIndent()


private val input = """
    hxcq
    xq

    iwokvx
    wtouv
    ikwvoc

    oedijfhxc
    divhjogecxf
    cidjoxfeh
    odxeichfj

    cuwvptsqyknz
    ahbtzvxjnsplye
    spvxhyizmnrat

    jkxige
    gikdqx
    gkwxic
    ixgk

    dubznrmlq
    izrbqum
    zrbuqms
    vwperouzmbaqj

    uqejzgwflkorvixs
    lwjuocfzpevsaqigkrxn

    g
    w
    y
    t
    y

    bqtycaj
    ktahyfc
    ycftga
    tacy

    gwms
    mtgs
    agsvm

    mucqvzntrfkhoiasydwxgb
    sxuvtwdmkayfrocznhqibg
    ksdignqyabuxrovzwmhcft

    kasubqdctyeporg
    aoeukiqdrypgstb
    vloewbyratgsfnqpu

    xgicmu
    xucig

    f
    sqkf
    yfn

    degobsjwquzlyxtmnci
    sbqgcxndetjlwomzyiu

    lbrs
    nyd
    gdpa

    yjwzdhl
    sykrzhw
    yrwzshgu
    hwkyzj
    htcfqwypvzx

    swy
    myub
    y
    kteyzpaf
    qcgys

    pcu
    cup
    ucp
    upc
    upc

    sl
    znc
    jr
    j
    j

    chytxmijvpdwnlso
    mhptcjdkxwrsfno
    mgtdcnhpxowjyes

    nhfbwglepzcyja
    pofzedxvhwn
    fhnsweipz
    nhqpzwfroe
    fnhwmpuzet

    thgjqka
    jatkqxgh

    usqxocdbnlrzvhgpyf
    tbpwmcjyixerzukonda

    fdcgoutihkqwxn
    wkqcgoixfdtunh
    tfnixcqkhwougd
    wcouhkdgitfqnx
    xtfwukdiogncqh

    jvotixefabumlqkszgr
    omtircseuqvaxjlfg

    lekjbvrysdatnqpzohgwixumf
    dtqvewzaukpbrncisjxyflhogm

    mkgs
    hgbl
    qg
    noydupgv

    bpqraxnwz
    wlhpznkdy
    gofwmuinsz
    wvzcnt

    yr
    yr
    yrb
    ry

    qamikgprfczy
    fqcaikyzmpgr
    gmacyiprzfqk
    cqyamfzipgrk

    paotrsxk
    rkst
    kters
    trks

    qirflwmjcavokzde
    hiowqrkdljavmzecf
    vwqkjrimzoldafec
    qoajvliwckefdrzm

    rkxdwjlemgs
    rtskaqlevxmfygw
    usghwrklmxoe

    ertxwvdqja
    dlftirewxpvqnja
    wjvxetrdaq
    gvreqxwdbajt
    wxvdgratejq

    gbsxeutj
    yniwqarm

    kcefhb
    xtlrdwajgy
    uglni
    lznqupo

    ynfumxhzkw
    ykfg
    kfsye
    yskf

    rjfmo
    mjn
    nmofx
    lxtom
    smugdkz

    bqelwfysvihpmn
    mlhiqswvpbynfe
    bpleshqmwvznfyi

    wohmqz
    woqzlmh
    wmnuozqhj
    yohmzqw
    yzohmqw

    syt
    sty
    tesyj
    yst

    xoeuklvphrabywjnqsz
    ahyqvbnxpsukzlrowe
    nrxhzplaseywvqokub
    zwpqnkxbsuoeyrahlv
    snrupxblzwokhqayve

    suhe
    sbct
    kwh
    ajzpdyf
    wu

    v
    ovf
    sav
    ov
    vgn

    glnaq
    ughaqnpl
    ablchnqg
    gqknltveo
    lnizgq

    duogkrbcpqvalntwefsmx
    gkexqpscdomwfulrvtbna
    drsjocnmtxkwyezhpufaigvbl

    twyhb
    tbw
    bxtw
    txwbl

    tmidkabyhlszq
    atlwbqmfzdkysh

    hsoudrtfnljgkqec
    drtuoezlks
    tlskureody
    kuslbrtedozmp

    wmdjelsxcibhk
    ciuhawksjxfbl

    odybj
    oydbj
    ijdyob
    jbdyo
    djyob

    bs
    wuza
    nbi

    hnfeuwi
    yundpfetkilvzwa
    nbcifewu

    phxkojfeiuv
    idhfauscrejx
    tiwyleqfmgbuz
    vjfcnriuoek

    njdik
    fby
    eaxitksz

    rfhtbuqcilxj
    jitqulbrhfxc
    baiqxturlfjcoh
    ruclqifthbxj
    uhfxljtqricb

    dbvkcezsxuojnmwtpqya
    vqtjdekxoabcznsmwyu
    vnyktcswabmezxdujqo
    tzbavysqjcxndkmwehuo
    wydujkvzsnbmeocqatx

    njbxp
    nbhpvx

    lazpkxushfvgtjec
    zxgpvatkhsflcjeu
    jaespfgzuhvtlxkc
    zlfphksjgcvxeuat

    ihgqbzswofrckt
    tziwkgfqocrhsb
    ezrdahsmokctgwi

    awpqchfmoislvrjkn
    qatjsknyigleumorzcw
    namiqokjwxlscvr
    olnjqikprfwadcmvs

    ultcos
    otulcs
    lcutso

    tbycsowgqnmxh
    djgqbvycwsxmolnh
    xzqscpaghwbyomikn
    mgcnsbhfjyqxwro
    beonqyuxwsgdcfmh

    ml
    ml
    gwulm
    lm

    jfmkqesync
    eudqimfjysknc

    rgszmuwylpabohv
    gavyumpkhnsbqzt
    zbhmpavugyeds
    mvgupfhyzasb
    pghuaybvmfszd

    tal
    akt
    amt
    lta
    at

    gjnresxcl
    vezytmbgqhip

    efqpnw
    dqpewnf
    pqfwne
    wjpfqne

    qudpx
    gkol
    pu
    fqde

    ystrhwjickgx
    rwhsnjkgbcti
    itrhskcgjvweb

    gelumvjdrhkwaft
    wurmhaedlvkfgjt
    ehfdwlatgvkmjur
    mthzjrgkweavdufl
    ramjueftkghdlvw

    m
    m
    m
    em
    mc

    yzinx
    ydipz

    qmvlwyf
    qfyvm

    l
    kl
    l

    oeyihdxvc
    hdyovixbce
    iwcfoyxvehd
    cydxievhoml
    cehvtxiqody

    fpwyrhtlx
    vpxywrgt
    grwpyxt
    tpvwryx
    tgrwpxy

    lripyo
    lrioyp
    loyiprt
    oipyrl

    crjwhsu
    schujwr
    jshruwc
    sjcrwthu

    vzimjkahfyulgxwn
    isnxmlfhyujzakovw
    azjfxbyhiwkulmnv

    ifaq
    qfi
    qif
    fiq

    nxkiocurqjzhyb
    hiscgvkjzronqtyx
    qxyzcokhijnr

    pigkma
    magkp
    mpakg
    gkamp

    dmbe
    bed
    ebd
    deb

    k
    fhk
    k

    uot
    gpo

    lsxhdvoaykcmfeur
    mrfeakudoyshcxlg
    kxlbisardoechuyfm

    ymjscroetwaxqbp
    iknfdzxvhlgsu

    xhdger
    qbth
    fzh
    hkwq
    hnivulsmj

    aizhdlqb
    iluadqzbh

    poxagnvidqyhb
    ongqhpaevybid
    hmapgncvyzdikxqb
    liyjrpgstabvhdnwq

    bu
    ub
    bu
    ub
    bu

    qboxarcdtjw
    shyezuwlpdtabcmgkjo
    wjidtrcanob
    wdiacbofjt

    esnjfd
    enj
    lexnqb

    xcbvg
    xcb
    xcb
    xcb
    cbx

    mceyldbqgrhnixvsfwjuaz
    msyijdznrlucwebxgahvfq
    cnvgehrjmbdfaysquxizwl
    zlfvnjsxbmqeahwurdiycg
    bgzrxlejhvmacuywqsfidn

    gzkhe
    hzekg

    vbdfgslypotqhjrmcu
    qcufbjgmedlstvph
    sulcfbgmpejqvhdt
    jspqufthdvmgcbl

    weolvftbq
    oqweklfhtvzbx
    eqowbvultpf

    lxewgikyh
    kweiyghvl

    cfgobijkte
    klcdoife
    opfilzeck

    eqjsokxgtfzbvunycdhrpm
    rmcbzytuskhedopjqfvxg
    rbuspxfoqjgkyhzmdcevt
    yrtmsxchbfkeduzgpjovq

    ozvbqdprajfuewntyh
    pmsjqlxbwt
    gwtibpjkqs

    w
    w
    w

    jpmiats
    smzjlp
    mypjs
    spmjk
    spjyzmd

    pvkst
    psz

    ant
    thya
    wjaxtsv
    chta

    owxpr
    umrqwop
    rwxpd
    wrsblzepat

    owprfilcaqgvzmde
    zqceralifgmdopvw
    mpwqgfcdilzveroa
    icozdfergpwlmqva
    cqleoraifwvgpmdz

    aoc
    cwha
    vach
    ahc
    dnbqmcreyza

    elub
    gzlbs
    bl
    bpl
    bl

    wbksuzlghxocdymvr
    kclvhmobdzgusywa

    rwsnzma
    wzsnl
    zswn
    pzsnw

    uvbqfoj
    tneoqdvfjx
    jqovwf
    rmvqojlfiacg
    fjbqov

    butxrpmaniezyf
    bfupmrvixytzhna
    tnycwiamrupzxfo

    xqp
    pxmby

    tfwxmayqgu
    endoxvrcuzji

    rvtgiklxhcam
    vhxigdaonty

    pewzvchdqrymsultjfoxk
    gfkwcjrletpunzhxvydo
    hfujezvprcwoydknltx

    nolria
    oirlwa
    rlioap
    rqalio
    ilrao

    ox
    min
    i

    gjvdfthoexbqwkrmypi
    jqvetrfboyhdipgmkw
    dvimorpjawqbkuyhtzeg

    dfpmvcntgkqworshajyexi
    wvaytjbopguhqredixfm
    jptwoihfxegrdymvzaq
    yqxvwpheridomajgtf

    cutgbiryx
    ugcmwhxyba
    spgeqdukyfnbclx
    gubyxcz
    bxygchouz

    nxpr
    neprx
    npxidr

    hyqesdpfwlobgnr
    vbpjtrkxewuzc

    ruvjg
    grvuj
    uvcgjm
    guvj
    vujg

    ptb
    bp
    pnbu
    jpb
    pb

    snolvyz
    lzxvboens
    oxzvesln
    osvxlnz
    zsvawlnoi

    iygudzqnjxrmpkolv
    pshncaubvdtfwj

    yjbd
    mwpbd

    x
    exg
    fjxy
    ywfjx

    nxb
    lnyh
    hcn

    ldqno
    ldtqfo
    ouqlgjdb
    qedgblo
    oqjnalygdp

    jrgnyhwkbeozixpdlq
    hlkavbneypozrtjxigsq

    zlcygoakq
    wyhmde
    vobfucitnyrj

    zhiyflevjmgcwurnkdtqspbxo
    qipkgufbzwrlynhdscemxo
    sprwloygundhcbefmkixazq

    lacsbevntuhimpozf
    alcxhzunoyvfbts
    losuadhtcbnzfv
    zulonhfdcjbvtas
    sfcbtlzaruhvgwon

    pbsdtlmqgvwi
    ahnumfltgxcqw
    ljetgpodymqw

    a
    a
    a
    a

    kzjwtvh
    tczkjvf
    nkvtyjfqlgsze
    bjroitzmakxudvp

    xpfhljyrn
    xslyphrfj

    uyoqgxzbmdwkfp
    fybgpoqnzuwdx
    fbxyuwqgpomzd
    fwiyaprgodxszcuqb
    pbwuxgnoyzqfd

    ukh
    kbu
    uk
    ku

    bpaxgdfznyhsqu
    djkxbphmazqcuf
    ufohqbtvzdxrpa

    leg
    lzexotig
    xjlg
    ndavmglkb
    xupglt

    rbnzumhyocaiw
    uryivzmoawbngch
    hmrbygzuoacw
    awyoucrmhzb
    uyxtbhpowarmcz

    e
    e
    e
    e
    e

    fkjubgmehnz
    mfbujdtzkxny
    znxlbqrkj
    svknjwzbip
    zghmakbjnxd

    el
    el

    tdiz
    ivu
    puriy
    rlkyim
    ioajshq

    cbgkwfyaenhdmtiuxpq
    igeuqmwbzykafxhtndp

    oxvmqzhgjsfpurybkc
    kfpmheotcraqszuyx

    od
    dogc
    od
    od
    do

    zpsdjaycmqk
    qmzpcysakdj
    jmpszkqaycd
    azysmcqpjkd

    lypkohvdiujsfg
    cmojfzrad
    ltxoqdejunwfk

    fyuwibvqen
    nuabsdt
    umcbpn

    zdr
    puzogym
    iza
    kfbzia
    z

    ecxrhmsnazgpjv
    grmajnpzevcxhs
    zvrexagjspnhmc
    exhgsjnvmrpcza
    rpzajemhxngvcs

    ayxuekmgfqn
    jqfxpugkm
    kwlmgfxuboq
    ficqxkhobrmug
    mqxklugfj

    uxqkyagefozsmhrbdc
    eokuqmcbhdyafrzsxg
    ywzogukrbxqdpefamsch
    shzbkdfrygqmocluexa
    rsymeczgxokaudhfbq

    wnprmqic
    qnrmwiotc
    crpqmuwnbi
    sgniyewvkjqmdrcz

    byflxmo
    ofvbmx
    foxbm
    xmfbo

    emqgzbkhvswpfartncy
    scurtpnfqyzhaoliebwdvkx

    bndwfgktrjmhsyc
    yzkesurtc
    yckraust
    iotksryc

    gpk
    sg
    sg
    gtv
    sgm

    fnuejwbcgrqykh
    kmifhcegjwnrybtuq
    wkngqcfhusjedybra
    lcsburejhgfnqkwy
    lwqyhkejfubgcrn

    z
    oez
    zs

    gyoq
    ygqxomb
    qxbvog
    odqg

    gfjardsilbucp
    rovwlepxcb
    ytxlbhprewc

    pihnyv
    ohyzdvn
    cyhkbxnv

    otbmxfcq
    tcqfxozmeb
    bcqmfotx
    otbqxfcm

    u
    u
    u
    u

    qpyflxtwsgivrmun
    dhrlxfngzwobysmkai
    cguswmnyrixqljf

    u
    vmipnu
    u

    dfubwctop
    cpbrukfdwy
    rducbfwp

    bltqnvr
    vytbgrmql
    bvlqjxt
    jitnlpgxvedqb
    klhtqsfabvuwoz

    fgrmxo
    rgofxem
    gmoxprf
    ormgxf
    grfmox

    oitfzbspwlmecdahxjrukv
    rajmxdstevfioklzhucw
    ickamoqzrtwugjdxhselv

    tlrbupqfoczgmjhxy
    brqumgtwkjpoyechf
    snbovthudfiqmczpyjrag

    diemtlphaboqu
    pgroihwnlxfmbd
    vhmdslkpozjbiu

    vfawq
    fwar
    fawm
    frwam

    sdgnzxjl
    jnxdlzsg
    zdlsgxjn
    nxdlgjzs
    ngsdljzx

    tbdpywlmvhfjnsiucz
    whjmoszutdlnpyivfcba
    zdmljnpwtfbuhciysv
    tmnvclkdfqgpzwijushby
    idlfnuwyjavspmchztb

    lwdjyeq
    trvijlbs
    rvbkasglj

    azfjspiovymlngedbtwqh
    zlnvbqdwougsraijy

    okxl
    ukhyo
    oykh
    kuo

    ebgshrcyuxij
    lqybtkix
    blixyk
    fxyqitb
    zxiby

    vuqxgbcdrjmtzwyask
    hajxfimseynvktz
    lyatxkojspzmv

    oflqgwpdcykxnr
    waycbduexkpglrfo
    xfglrvtpcodwyk
    rkxcyfldowpg
    rkpfywodhgtxcl

    frndqbe
    dlefpb
    ferbtdv

    girn
    avlhin
    tvin
    ioncmz
    hnki

    ftmdnoqvhucbwgxe
    xsjaimuzrqgyk

    sckxqrldhnywbmapeitzu
    axpsniudhjwybrtqmklzec
    tkmrxhfacsbzdwnlipeuyq
    bmesqzxhltunrcpykwdiaj
    camwrlbensyxkdqpjutzhi

    wuinhqfsycalg
    ichgwlsnuqy
    uhlicswqgnty
    wsygnihqucl

    m
    me
    m

    dkro
    cgxy
    xeuh

    atpjqlkbhdonf
    lbkphyotnsajwfqxdu
    dloabtknfqjph
    fthpoakjbendql

    npyhae
    nayhe
    enahym
    ayehn
    hpyaen

    qjgvhukoytpec
    gqepvychkuot
    pyiuhoqveckgt
    bvuhqykgceopt
    hbgvepukyotqc

    qlgbo
    valb
    lboa
    xhebnlmdw

    a
    a

    cohbewnvizukdxytfm
    nctvkyewbdmfoxhzuir
    iecvzdbxkwqofthnmylu
    cvfspxodhtwyezmbukni
    ejkiafumwtvzobhdycxn

    yvj
    jv
    zvj
    jv
    yvj

    sbumytfaqi
    bqtofsymgruiac
    akxqinwfmyzjthusl

    kwjqihdgzo
    dhgkjoizqa
    qudcihjkogz
    jzihogqkd

    f
    e
    p
    p

    g
    g
    iv

    rxqngstoevh
    lhvfenqxtjbgrso
    tnkghrvoqsxeu
    gkxpnhtsvqrome
    qmgnuxtovsehr

    yqwkivalso
    yapolqsvi
    djylnqaviso

    fpoq
    qpecauvwj
    bpoyq

    rt
    r
    jrim
    rt

    rmqyncatd
    jya
    syvof

    a
    a
    r
    j

    wvsozfl
    wzksfvloa
    sflovzw

    ugjlwrz
    rlgjz
    lrzjbg
    zjrlg
    grjzl

    fjdlvikxuczqbgs
    jacfbvdzgxui
    hcxvmorgbezdjfuy

    iafzulwgcymenxrohdv
    lisncjkhqwrfuzvtgomdyeax
    ichdfnyvulxroaezwmg

    taqyb
    rlnftycph
    ygexjhl
    vudimswozyk

    estv
    estv

    jylpt
    yrljus
    ayjlp

    q
    q

    lmwifkhyxv
    mykfxvli
    pfkylemvqitxj
    vfmilxsky

    zgbmjqdaxlytkrswe
    qkdrxlzgswbjtayem
    qywtdalkmejzsxbgr
    lmtkbyedrzfijwqaxpsg

    alopehcb
    eycuaog
    oeca
    cageov
    eoca

    uhiqcknobmvglx
    ybvunkhq
    qnubkvh
    uqnhbvk

    qpvrikdhmeounazxjytcf
    fozyhputjicrdbnae
    rtfdnzsuoilghapjy

    jxsreicubdnywaq
    xdiunfjrsbqywagel
    amrbnwxqesyuzd
    obsunwiyeardqx

    cpmbrzfluyhqxijn
    fimpulqbnjzyrk
    ovnpyqbdlzrujeaim

    enxdhzlsm
    exdlmsin

    vu
    u
    ju
    u

    pnkwhcfu
    uwfckhnp
    ujfhpnwck

    q
    v
    v
    v
    v

    a
    wa
    a

    flbxpcr
    kxvclbfp
    baclfxp
    pxbfcal

    dbvq
    qdt
    lpqds
    hmxvtqdb

    nwhtmvbajdgzkyq
    twcakfgmxjloqhdv
    gwmhvkajzqlfdyt
    gpmudhvstekrjiaw

    b
    uzh
    o

    dcpfokxehq
    expqkhodfc
    xoqkcdebphf
    ocdhxfkeqp

    anvqxof
    snlahie
    bprwgymukazt
    ahjcd

    bnywqc
    tymwnqkbc
    qbnyawc
    bwqcny
    wcqyjnb

    ptsjimanrudfx
    imrafuwsdxntj
    ftnjirsmxaudy

    nj
    nr

    tjubk
    jpbuog
    ubpj
    bpuj
    bju

    cdpvaozywlxgnf
    fyjgopxdlazcvnw
    mlwopycbvxfangzdr
    zxpgonycfavwld

    uhqaptzcmlgbiexnryfsjw
    ajbhnesfctlqzmygiwrxup
    qewahjsbinmlpxtrcufzyg
    afbjliympwzthnqgcuexrs
    iqjmhprgufxctyzelbnsaw

    astpu
    ptu

    yshrmkzbljeqgtudxopwn
    ubqrltjkxpmgesovwhy

    swpuajoeticdgx
    gjdstaucpweiox
    sgdaiowxcptzeju
    ojigactpudxwes
    oahjicgxtdwueps

    oivrc
    hdysiwa
    iaktwfd

    fnh
    pknh
    dwnmbcsh
    vlfhngar

    eaifkghjtmv
    kvafjgtzem
    tvjalfgqkme
    jewftgvmakz

    hbnqcvil

    iprhznajgqwtdlv
    vlrnijtadhgzpweq
    wqtinvjpdlarhzg
    rvdgajhwztnqpli
    hlwjqiapvdtrgzn

    hvqy
    qetjlpburkwgcx

    ox
    ox
    xo
    oax
    ox

    zj
    zg
    zj

    lzotnqd
    lonqtdz
    lqzdtno
    zntgloqd
    qnodztl

    xkguhdybcerftmiwvsj
    jefxmdthkvrbwgysciu

    qfbgrjlhdpontawmxvicue
    nhcrqpdjamtevuxlwiofgb
    tjhxgmwdrnlbfacuveoiqp
    axcwmugdvrtijqeonhflbp
    ramexutvgqhbdlnjowcifp

    efdqwmcnoyitjuavlhszkpb
    cbsukvhoqjndpfzwtmeial
    ucgdqajvtxzmsplwbefniko

    yi
    d

    cwx
    wx
    vx

    ud
    u
    u
    u
    u

    xihbpouvkfsn
    kapxuhoivns
    sluneypovtzwihk

    k
    f
    kcej
    f
    tb

    havfjbruzomwcilqx
    xwhtfmjenqzpasbdru
    yhfgudrqzaxjwkpmbe

    cdw
    cqw

    way
    aqy
    yajq
    cayz
    aqyw

    ugsp
    gpus

    iju
    fij
    ji
    ij

    agcbqvr
    cbvrqwg
    wgqcrbv
    rgobwvhcq
    lbcgrmvtpqu

    ibyvklzejpghaduwoc
    iwpsuxkgjcevorzdhflyqba
    hpulvyzkwbatdgniomejc
    udohpvwygjiblacekz

    yfhjnvurbsqiaktdmgolcxwzp
    stvjogzwcqribuahmyxdpnlf
    tnhbgzxlwyoqvcsfujirdapm
    ichuzjbagxlspdvmfntwqyor

    sh
    sh
    gseh
    dhsy

    zefydtqhn
    wiknztqbeldhfjvp
    eqftnzhds
    tnhefzcgdq

    cilnbugxskyhawzejrdqvtfpmo
    ibkdevntzpjaxrowfylsqhgucm

    enfzuawmtqyjlckih
    pzmtrdfegkbjnlwi
    itlbmzxfwkosejn
    ldkeznmwjitf
    xwlkvfzejtnim

    qwve
    qvdwm
    fvwiq

    rhwgqysimdlzjp
    xufelghmspck
    hupmgnsolt

    kumngw
    nugkwm
    mgwuxnk
    ghwukmn
    wugmkn

    wfz
    wzf
    wzf
    fwz
    twfz

    tvbuqspnl
    sktfcypxnwli

    kr
    h
    h
    isu

    j
    jb
    bj
    jrn

    lsmjikfhotquz
    ijotszqlxf
    clinxjetvqsfoz
    bfroacsvqjizlet

    wcdqhzibrpkfst
    kqfpwshztrbc
    wrbftsjhkqcpz
    ocbkghsewzpqrtf
    kbcftjspwhqruz

    rxabd
    dbrf
    brdnw

    c
    cv
    c
    c
    c

    nsirkhqo
    qloiwrtksh
    hkrmsiqo
    hisrkqo

    pomgqraevbwy
    vrbyaqgwpoe
    gjyiqwvfkacrebzpo
    pbareovxgwyuq
    yrgenvopqabtw

    k
    k
    k
    k
    k

    othunpecyjivg
    wxeutfypnikrhazvcg
    tbchmynvusgpied

    qmraxhozfjg
    zjgamsxoqfr
    qrxfzjaogm
    jgxymaofqrz

    zcpvisk
    kizevcfqs
    scbkzavnit
    qcszjkfvi

    anrmcpxvhdtj
    jpthxawmd
    tukzysdpmqjgaxl

    sbmv
    jwzmkpb

    piwsxmubfckdejolgnzqh
    gxicnhpljwmbqfuedosk
    jqpunwoklmfhecgisbxd

    zuby
    urymzbt
    wzefybdu
    ysbzu

    okasiyephflnczbdmrxqjg
    qmsbrjzxfopdiyghelncka
    xqfgznlakhpobimyrcjesd
    urnzjkcaqvfohtbmixespdlgy
    ypnbxmgorshijcelkqzafd

    isuntmjlczadgqvohp
    qdvnipmachtgzsuljo
    hcmgldupazvstjnoiq
    acntqzuipjhgmdlsvo
    vdplgsojhncziuqtam

    rwyqk
    wynksr
    rwkyq

    sxpwkltiy
    pytcikwlsx
    ikswltxyp
    ltpiwykxs
    tkpsixwly

    utcalkozneig
    unclegzioawkt
    cztnimglbeokd
    oecgnlikuzt

    mtfdkczqaobxnlgespvyuwirj
    gwzeahrivcqxotyjfndulsbmp

    lrboxdijnuasyqw
    vcgthpsx
    extmskf

    ojpma
    opajm

    g
    g

    zbixqhucwalfgvyst
    witvhfbxuaqlzgcsy
    qtwcybluvfahgszix
    fbzwsugvtyalhqxci
    lszhxfwtuvqcibyga

    zwgircqhsunk
    crkiunqwzgsh
    ukgcirwqzhsn
    skiwgqzhcrnut
    ircsnkzqgwuh

    xhulpcrkgobmsnjeqdy
    iqjsrogkfcwhz

    aes
    xejplcs
    hwesf

    afxuytvbqpmsewcj
    vykgpclomzajwtxbfsiqn

    tfbekaqzysimxdrhw
    xrthuzkeibsdfqawym

    kgprfalqenuhvm
    ghnzfqpvaweklmru
    hkeglufnqvprma
    nlaehuqmrfkgpv
    fhrgvmqkuaplne

    u
    v
    a

    ytwqkmlnf
    lyfwqatjpnmke
    lmwynqfbkxgst
    ntwmfoklyq

    moyve
    vmye
    mvye
    gxvemy
    elvym

    euqaszvrbtjm
    ogerhcn
    piyxengrl
    perwlxh

    povmbjnuyrhkcegsfwazil
    weqjmruszicnkvoyabf
    krufijysmzebvnwcao

    vnaerbl
    navylb

    fcekoi
    wiobsxtc
    cykejrohi
    cifor
    hnicof

    cow
    wa
    rwsmuz
    afw

    jrmcbzldwve
    qleft
    plnuiha
    frlsxbwcg
    oykzrgjl

    tvfyzicslqr
    scfyzvlwrx
    zvfscrldy

    grdmazyhj
    ujagxhmlwyri

    mknefwsxzuyqlhrp
    zqkelxwnyhsurpm
    pwuklsnehqxrzym

    jq
    jq
    jq

    owxmnyebpa
    pybnemaxow

    jongwiczqelyu
    uiyovnqeczmkwjglp
    clzjnrwfuieoyqg
    qonlczjgfwtuiyed

    cy
    yc
    yc
    ytac
    cy

    mzpcohwyqxbsedfjiakn
    ibnwadkjhoepqxfzycs
    jazchwoksxribdnfyep
    fpvseaiycnwhxkojbdz

    bk
    bds

    oj
    h
    mxy
    d
    zj

    oikrnufgejydav
    sodjgkfnharqeu
    utfjepadgbnrk
    gdkrezmfwluxjan

    phvyodqkwcr
    bziatjlxnu
    lgfmiesx

    fwryqhmlti
    sztgyhlqrm
    htnlmqzf
    dqhcbxtlm
    hmtqly

    uljdkpegztcs
    mbynaophifvdwrlqz

    tkzyoqfjx
    zjyhoxqf

    erixpvcqtnodlzabums
    ebctlzdxinvpqaosw

    gkthjzpoebwyclrxs
    hzlwsbtoxygmpckej
    eosbkictywhjpfzglx
    slgtxjzwcekohypb
    xlwtepqhnozadjsuvcykgb

    kjasbwpcmihdoyfxeqtl
    jmcotpaiwzhkysdflqxeb

    lxvmpgt
    gvxtmpl
    lmvcgptx
    xtvlgpm
    vtxmgpl

    ijuadevfkogxn
    yfuitkdvjhgoe
    vtyidfeswjkugo

    oqcb
    bocq
    boqc
    boqc

    oqtr
    tobrgpq

    xoglfme
    dn
    qryjbz
    uy
    stu

    sdtjvxqbkznwfpa
    wdjsfztkxqbna
    wdabstfzqjxkn
    qfdjnbkaswtxz

    nglrsqpm
    nmpsgqrl
    gnpsrmql
    gmlspqrn
    qlsnmgrp

    mcuxlipqgasytzrofe
    ipqlhnygecftraxbuoz
    suclpaxqirzyfmgoet

    yjcpmawlo
    tdpzgxnfaqvi
    bdhrpugzae

    efoq
    rqunw
    hsyltg
    juaev
    vwcuzm

    mxzg
    mxgd
    xgm
    xmg
    gmx

    pwkyrnuf
    fhvpikyncq
    peknfy
    zpknfuy

    iqgrfzhsywnjvldb
    zjwksuayiqvnodtg
    zvnmjyiepgaqwsd
    ntijacwvqxzpsdyog

    t
    tn
    t
    t
    t

    huweczmkfyqgsotja
    kjswqthmzygofae

    ubahfwikxtvqlryomz
    bwayhoxzfmtkurivlq
    twkaxrobfyhvqzimul
    zbqakyiovmxrwhftul

    wlfgqo
    fwlgqo

    pzn
    oqi
    i

    cgpkbovzrfhumjdx
    bghxfvudwmrozlkcjp
    ndcemtvibfqpkxzrgojush
    pkxafjdgvczoubmrh

    xbophwdkzrct
    jbckwxlthrsdpo
    btdywocarphkx
    wozdchbrpktyx

    urbv
    finzs
    hfi

    imlodvakutwyxhrbnzcjeq
    rvclbmzquwetdnohjxyik

    bxhe
    xhe

    gsohmjiqp
    oqimgphsjkxa
    ighspjfvmqo
    himpjqosg
    mfgvhpsjqio

    mjvhbdnarl
    mjdtilcu
    ljdm

    xdose
    pswd

    cmrqwte
    mtcrqew
    mrcwtqe

    ktjbroaxdvgmzyqinc
    ivytbaxgdcomqrjknz

    tycbkg
    pwsozmbh

    dzahkwuljqg
    yaxwnzgsoei
    wcrtdgfaz

    vkyje
    eyzvjk
    evkysj
    evjkyz

    kgoajmhzlt
    ohrlka

    ajrtwuoiycmgbqdsp
    jitzoaqcnuybwlvrsg

    oziafwgb
    bqcopeiyvgmw
    bzglidtwo

    jzxqfuyponh
    psognqhjykxlu
    rtcyjdbeuoqhnpia
    uohnwzjsqpygm

    ysrznohemgqjavu
    mtjilwhdfpx

    tqukago
    ojbka
    kot
    okcfrshxv
    koag

    ztvkhbgwpdi
    htpvzigdbkw
    ibhawdzkvgpt
    tdkgvhbwpzi

    fv
    n
    ohp

    yqndvjx
    nqyxjdv
    yjvxndq
    nvxqjdy

    joepzmtalkuiwvbq
    fvietbjmopqzxw

    ogxbkfwvzj
    gwxkbjfzv
    kzjgbxfvw
    jbvfgxzwk

    cxyaipomkwqen
    rhkoymjieucxaqf

    gafuzdrvpmjtsowc
    cpgrzstfujvadwlmo
    atdrjpcmsfguzowv
    sctoprvfzamudgjw
    cvojptgdmazfsuwr

    yfxqzuold
    vdcgmwohti
    rdkulo

    yt
    sdz
    ade
    fko

    ufhtiyplvwarmdezqgx
    wvydemgrqkcahulpfzix
    hparxqylvmdgfwizteu

    ir
    ri
    ri
    ri
    ri

    pqub
    buq

    uzbfo
    fhou
    ufo
    oufz
    ofub

    qmjbizlgyw
    guyzklwfqhb
    ygqezlw
    wnlycosxzvatpq

    xtyi
    fhigxt
    xitl

    lfbspdkuxnwma
    psudkfwlgmbnj
    klybwfnudpmgs
    pumsbokdlzcnwf
    fbswmknpdlu

    tcayg
    vecxtrah
    amtc

    jpft
    tpf
    p
    hrpxi

    i
    si
    i
    i
    ix

    hizroqkstnldpamxeg
    tkndalszrphmgqeoxi
    xeknhqspgalzrdtiom
    dkopxzlqestmnhiagr
    kilnqmtxazrposdehg

    lxscnfpgo
    npldfqocsg
    nxofpsgcl
    ocjsfugnmpl
    gwpcvohsnafl

    nvquksbcwpzdfy
    ecxshpdgqkw

    pqxtrwovmj
    dbkn

    glotbqwyvsapfnkrjcu
    bclrpagfuojykstvqwn
    youfwbratnjklvgcsqp
    ygftraocqwvjlbusnpk

    wqiojy
    oiywqj

    urdjmbplhxfvwnsgzak
    hlrswfkzdbvujmgxpan
    vhuprgawmszbjxdlnfk
    rkvlhdwmabsupjfngzx

    wopkrgnebizj
    pztiwrbngje
    erwipjgnbzm
    ebirwgzmntjp

    wtxumael
    txepljmnsq
    tiumoxdlae
    lmxetak

    tor
    ojtxr
    rotq
    rtgo
    rto

    uifjdlxgp
    iamdbyugj
    dvoehrtskw
    zgimqyd

    jiwtp
    lmshtxfkaygv
    otpz

    dfv
    z
    tp
    ukfayvdx
    rqwjiel

    um
    mv

    g
    r
    g
    g
    g

    hualsknwctrgpbodfj
    iqzvunfothrwbapmjclsgyx

    bnrdiqhfyomz
    ymrznbfqihod
    ohqynrzdbmif
    yfbhinordzmq
    yozmbrhidnqf

    pnjoqmvxyktrlde
    odpqrnyleim
    omrfpegclyndq
    pqlresognmhauzyd
    erymlpnqod

    adnlyhjkixe
    xbkflydqntaew
    agmupdrzksyxln
    doxnvkctaeyl
    kxaywndhflj

    snofb
    cnsbfo
    hnfsbcom
    fobsn
    fxoanvslb

    wdovmxkhi
    phdvikmx

    pdxhostezmfayv
    hdgopynuztixvcme
    wtzxorhmlb

    rijvqkhawzunmlfdcyo
    dwiapvqjuxorehzmbn

    nm
    mv
    cwjrfxbudizgl
    kq
    yhe

    dtzpcjaugwvy
    cwdvspjlgatuz
    ufgcvwitajdpz
    kecqtzdgpvwjuxar
    ubadtpvmwjzgc

    qcdzvtmuaog
    guqtaczvmo
    qcuozvmgat
    ucvoqatmgz
    uzqtcgoavm

    psfmalu
    pebstlfaz
    afurslp

    movq
    mkeavq
    xqgnmv
    nomvsqx
    qmv

    pgbfcakvdxlhz
    acxlskwzhdv
    lzdakvsxch
    cvwdlkaxzho
    hxvzkuacld

    adrzqxlouhnptfgcs
    bwzntkiepyjmdv

    v
    u
    z

    rvf
    rvf

    iothywzujmdcqxgfkalsbpvn
    linwhdambozvefqpkstcguxj
    dasqgpzfcomvtkuiwxblnjh
    hcumpairnxoqsjldkwfbvzgt

    ahlnzpjouirmfds
    zjcmhrfaidosntupl
    sadfjlniorpuhmzwk
    hurfoimaznqjdlspgx

    kxcndips
    dqkci
    incjzdk
    udvcybikthl

    ycxkrjlesgv
    kliendoymxf
    yhoalpfikxze

    szmjw
    sjitzm
    zmts
    apzsm
    szornmf

    pdc
    rhdpe
    kdqxup
    dp
    dp

    k
    ok

    vmjlghcxut
    nyarkbqsofiw
    mdxjuzlp

    a
    a

    qywlx
    thkfysgubj
    zyoq

    avfdzmhbpnjkrweuotlix
    ljpikqhxemdunorztwvbfa

    whycifunlzrxopgjbt
    ndvkgjiemqpzxays

    utje
    jutvl

    ungaqsxzfhtdoj
    onfqjtubs

    jfzsvcnhy
    jwvhzynisaox
    vnhqrkxsjylb
    deyhusvgjnm

    ul
    ylw

    kgextpilzwahcvfbs
    nptvbcfwglskxaehz
    hvscefwtblxpkgza
    bfavdtjlhwgckexpzsy
    pzewxgaicvqtlbfkhs

    c
    c
    ca
    zm
    a

    nlivuzbkgacd
    kavbzulmnswcig

    fjhzscibgay
    cabkiyofnlurd

    uxz
    zux
    ouxzg
    zux

    gwyfqcxbmkn
    goamycwnkfqxib
    vbqgfxnykcwmuz

    lztisamkhnbgpqvyjdwcre
    thlymdfcawvrsqbnkpegjzi
    jzgwhcnlmqiardketpvybs
    lykzigrqsbmwdcaoejnhvtp
    ztjaqdlcmkpnyirbehswvg

    ypgibnhc
    bhgnci
    bcmihgn
    gicbnh
    ingchb

    zoasgbrjfeykxhupdlvt
    uotjdhfaxrkwbpg
    bhfjtdxkpiouarg
    fkjgurbomdhatxp

    ifqjtbyupnvgkelx
    xhkvlotseafdbwn

    pxsaoh
    kfimezuog
    sot
    tovbl
    aqcol

    hrosqdm
    smjrw
    wymsr
    smr

    qskweubyitgldc
    glbkqiwetsduyc
    yuiltewgcqdbsk
    uvqsyegbkldcitw

    xfziwq
    lqvzaipwd
    zitqw
    cfkqzwih
    qigzw

    xsbm
    btilsryw
    xbus
    jbs

    ptfylczdxi
    tlxcpfyzmd

    fxemjbygva
    vbfmyxjae
    yfaxmbevj

    gdsqu
    sudgq
    ugsqd
    dqgsu
    usqgd

    y
    y
    y
    y
    ya

    wniryxogfe
    xyonefgcir
    votniesmuxgyr

    aizobfmthq
    mgnhqbfiwza
    ufrmaqtzbi
    fqlpaizbm
    qiafsxvbzymec

    qftzx
    zqtf
    rtpibfszqdn
    qzft

    uzvrtfshobkxy
    uiclanejgshpmqd

    xsntfplucwkbrgvzhqd
    lcxubjsgfrdkt
    gxidkscrlftubo
    rkglxsutbfcmd

    ozpfm
    bhjtnylpr

    m
    w
    w
    w
    w

    jayelkgtndufqixszmpcb
    ztbmfljqanikdeyscup
    izkusmqaecbldpyjftn
    dwqvscnjzabfelptkuimy

    kldgewtujmfv
    tgduklvfmwe
    gvdfkewltum
    kvwrdtflgmue
    wvteudkmlfg

    utvlfe
    pyvmulfnt

    wjdrglp
    fgolpmdbwjx
    ugdwhplj
    tdpgjlrwih
    lgwjdpi

    qgtvopksbl
    qlzhwbgtopv
    tqrvdcimlgbeopj
    ogqbpyhvnflt
    qpgowvbxtl

    tefvm
    uefymvhp

    gwekjhsmo
    wokhsmdgej

    jsh
    q
    p
    ah
    q

    kglzdmojsaihcy
    ajshioylmzkcd
    hiylmaosjzckd

    iyqkstpblxmvojz
    cvhgpqijfsel
    cvphdlqsgij

    a
    f
    a
    l
    hyue
""".trimIndent()