package com.drinkscabinet.everybody

val testInput = "ABBAC"
val input =
    "BBBCBAABAACABBCCCABBCBCBBABBACABCBBCCAABCACBBCBAACACCBAABAABABAABACCCABABABCCCBBAACABCABACCCBACBBBCBCACACAAACBCBABABCACBCCCBACBCCBBBCCBCAACABABAACBABCCBCCCBCABABBBCBBCACBAAACBABAAAACCCBCAABACBBCBBAABACBAABBACCCABACCCACABCBACBCAACAAACBACBCCCBAAAAABACAAABBBBAABABABBBBCBBACACBAACABACCBACABBAAABBACACAAACCAABBACBAAAABBCACBAACCBACACAAACBBBBBAACBABBBABACACCABCCAAACBCCAACCACCBAABBABACCBAAABAACCABAACACBBCACAAACCBCCABCACBBBBCBABABBCACBABCAABACCCABBBABBCCCBBAABCBBCBCCABBCBACACACBCBBAACAABACCBCCBAACABCABAACBCCCBBBBAACBAAACCACCBAACBBABABCCBAABAAAACCAABBBBBACCCAACAAABBCBBBACABACBCABCCCABBCACCCABCAAACACACAAAACAABABCACBCBAACCACACAABABCABBBCABCCCCBACBBCBCACAAACCABCBBCBAABAAAACCCBBABAACABCACCBCAAAACCACBBACBACCBCBCACCBBAACBBAABBABABBCBABAABABABCAACCCCCABCAACBBCACABAABCAACBCABCBBAABABCCCCBBABCABBABCCABCCBCACACAABAACABACCCABBBCBABBACCACACACACBCACACCABBCCACCABCCBAABCCAACAABBABBABBCABCCCBBAABCBAAABBAACBCBBAABAABABCCCCACABACBCACCBBBCAACAABCBBBCBBCACCCCBBCABBBBAAABCAACBCACBAACABBCBBCCBCBAACBAAACABACCCACCBACACC"

val testInput2 = "AxBCDDCAxD"
val input2 =
    "ABCACxDDCBCxxCDADBxCAADxDDBBCCDxACBAxDCCBAxBDDDBxAABCACDABACCBADBxBCDBBDBDDDCDCBADCBADBBDCBAABxDCBBCDDCCADACAxBCDxxBBDABCDCDCBBxBBCAADxDDxxCBDBBDCCDADBDBBBBABCBBBCBDAxDxBACBBxCAxACAxCDxBAABCCBBBBDxxCDxABxBBBCCDCCADADDBDCAxBACCDAxBADBxBDDCCDDAADDCDCBAxBCBCCCCACADCADxDxxxBxDDCDxBxBBCCDxAACADCBCBCBBABCADAxCBxBDACCDBADxCAxCxxBDDCACBxDxACBxCDxABBDDBxxABCCxCDCCCDBBxAACBBBDAxBDBCDDBxCBACCBAAxAACCxDCDACDBCBDBCABDCDBCBCDBAABxACADCCBABDDABAACBBDADABBCDDADDBDxxxACBDBxCDCCDABBxDCCABABBDACxBCCxCDBACCCDCCBCDxDAxCxDCACBBBxBAABxAxBAADDBDABDDDxDAAACBDBBAABACCAACBDBDCDxBDDBDxxDACCBAACCDxBADBCDCAACDCACCDBBBABCABxBAADADBCBCCABxCDACCACADADDADCCDCBAABABBDDCBDACDBxBBCADxDDABBxAADDABACCBDDCBCBCDBCADCxCBBCBABABCBDAABCCDAABBAABADxxBAxDABxDCAxxABBBBADDAABAADAxDBCxABBBBCBABBDBDCCDBADDBDDDDAAACACxACCDCDDDDxAAxDBDBxCADxBCBCCDxDAxCDAAAABBBCBxABADDAAABCxBDxDCBABABCxCABBBADCDDCxADAACBDDCDACCxADABBDxCCACCACADCCDABBDCACACABxDADCxCDACACBBBCAABBAxBxBAxCxCBxCxABDDCACABCDxCABCADCCCADCDBACxCxBDBCDAADBADACCDCBCAADCDBBCCxBDCCxABBBDCxCCBDBDDCCBxDAAxCCACBxACCxACCBADCBADCBBCxxDCDCBCBABCxCCxCDCCCACxBAAxABBADBBxCBAACAxDAAAAAxBCBCDDDDCxAADADDDBAxCCDCBAxAADBADACCACBDADxBAACAAABxBBDBCADxACAxBDBCCDDAxCCCxBDBCBDxCBDBCDxDBBDBCCACDCBBABAABDCBCAADCxABxBAABDCDCCACDDBDDDBCCDxBDADxCCBxCDDDAACBCCACBDCxCADCABCxABAxAABxDDCDCxACCCCDCCxADBCCADADADCBDDAxCBCCCCDABAACCxCDABxDADCDDCACCDCACDDACDBCBxCxAxAABDCDBBABABADACCBACBBBACBDCBCADxxBCxDCAADABAADBCACDDDBDACCCADxBBCBBABACBACBCxCDCxAACDBACxABCBCCDCCABCBBDBBAAADAxACCDACBADACDDDAACACBCCBBBDAxBABBCBBDBDDDBCDBAxDxCBDBCCCxABDABBDCAxDxxDDACxBABBAxCCABADDDCCADACxAADACCBAAAABCBDDCABAABDCBCxxBCBAACBxACBBADDxxACBDCCBADCBBDxABABDCBCAABADACBBACxBxBxBACDBACCBCDAABDCDCABDxBDBCDBABDBCCBAADDDBDDCABxAAACxCADBxAADxBxDAxBCADDBCABBCCBxxDACDCDCBBCCxBCBxBCADBBCABBDBDBCABDBxABxACAABDDxBxACBCDDBABDDBDBCAxAACAACCxAxACCBCAxDACCCBCxBCABDBCxBCDCBADBADDCDxADBADBxBxACBDCDDDCCCBxDxACCABADDBACCBCBBDCCCADBCBxCACBAxBCCABCDAADBDCACDDDxDBAADCAABCBxAABCBBAADDABxBCDDxDAACxCBDDBDAxCBCBDxDADDBBABDDBxDACxABCDCDADDxABABBAB"

val testInput3 = "xBxAAABCDxCC"
val input3 =
    "AxxACxADxCAxBDCABCBDBxDCCABCCBCAxCBDxCBDBAxBAxBDDDCCxAABCADCBACAACCxCDCBBDDCCDxDBBxxACABCBDDCAxCCxAxCDDACCCCxDxBBBBBCxCBBCxBDAABBxCCCAAAABBDDBxDCBAxxBDCxADAxCCBDABCDBxxxACBDBxADDABDxACCABCxxCABDAAAxCDDBxDACCABxBCBABBCDxCxDDCCDxBDAxABxxABADCDABBxDCBxxAADAAxCDCCxxBABAAxDxBABxBBADDDAAABBBCDDDCBxDxADCDCDDBDDxCCDDDxCBACBABADABCCxxCCADBADDxCDADBABBBxCxBBDAABBxABBDAxCADCBDDxBACxDxDBBDDADDBAxADDACDBCBBBBxADDDBBDAABDBBxABABAxDxBxDABCADDCBADxCxDBAAxBDACxBACAxCxBxBDxCBBADDAxCBDAACCxACBDCDxBxDBDCBCABBBAxACBCBADCxDCDDCDDDxxCBCCCCDDDAxDABBDDBxBCBBBCCBCCDADBDDADDBDxCAABCCAAAxAxCABACxBCCxxBBACxABAxCCCCxAxAxCCxACCAxxAxBDDDBCDDAxDxCDBCBDBDBDBDBDAADxxCBADCxxBxACACACCABAABBxCxCCCBBDAACBBxBDDxAAxDDACCBCDDABABBADBxCBxCxBDDDADABAxxCCABxCBxxDAACBCCxBxCDAADBBBDCABBDxBBCACDACxBCCCCxCBBBCDAADDCBxDxAADCCxDBxDBDBCDCAxDAAxBAxCxAACDAAABDBABAAABCBAADCAACDAAADABABBACBDABCDADBxxxADDABADDCxDxxDDCBxAxCAACxDxBAADCDACxDCBxxCBxDDABCAxBxxBBBAAACxBADAABAADDCxxBxAxCAxAAABDDCxCACAADxCCCDBBCCxxAxBxADxxCAADCDDxCCxxBBxxDxDBxDxBADABACAACAxAxADCBBAxxxxxxxBAxxDxDBDDDDABBAACxAxCCCCxxAxCCAAxCBCBxCCAxCACCxxxxxBAADBxCCCCADACDCxAxCDCDCDBCBAAxxBxDBCBDABACCAACxBBCCAADDCDDACBCBCDBDxxxCBBxxDADACDBCBxDDCDCBDAADCBADxCxADDAAxBDCDxBCADxACABxDABxxACCCBBCCCAADBBxBBDBADCxCCCCBAxAACACAABBCCACxxCDADxACxAxDDxDCAAxBABBxCAxBDDAADBBDxBBBCDxCCBBABBCAACxBxBDxACBBxCBCxBADADxADADDBBxDDCABDADDAABBBCAxxCDCDBCBBBCCxCAAxBACDAABDACxDAxDxBCDBDDBABADAxDACDCAxAAxxBAADABCACADDBDxADDxCDDAxxABAxxBAADCBBDAxxxDDADAADBCAxCCDBBCDAxBDCxxDADBBDxxxBADBBCBAACCDAADABAxBxxxAxxBBCCCBCCDDDBACDAxCxDxDAABCCBAxCBDCxCBDAADBxBCxDxCCxBAAADBAxCACDxxAxDDxxCBBxCDAxAxDCABADDBxBCxDABBBADAxxCxCCAABBBABBxBBCCBDBBxxACCBxxBBxDAxBxxCCCCDBDCAAxCDDBxDDDDBDAxDCxADDDxDBABxxBCACxAACDDBCBDBCCCCxxCDCABABCDABxCCADxAAAAxBCCDBAABxxBBCACBAxCxAABBAxBxxBxDBDCAxAxDxxBACCCABDBCCCxADCCCxCBxDACDBCxxxCBxBBxDCxxACACBBxxDACDCCxxDxCxBACxBCxABBAxDDCACCDBDAABBxDAxDADBBBBBDAAxAACCADDABDCxxDDCACDAxxCCxDCDABxBAADBCAxxDCAABDBAACAADABxBAAxABxBBxDAACADAADDxABBACDDCAADBDABDCBCCACBBxxACCAAxCBAxCABABCDDACADCxAACCDADABAAAxxDxxDDCxDBADCBDBCCCxBBBCBCxCCAxADxCBACDDCCCACxCADBBCDDBCxDCBxDDABCDAxCxxCDCBCACxADxCACCDABxCxCBBDCCABABACBxCCACxxCxDDBADABxxCBBCxCBACBCDDBCDBDBAxxCDCBCxCCxBxAxDDxBCDBxDDADxDBDxDxDAAxBxBCACxxAxxBBxDDDxBDACDABBAABxxBBAAxDACAACDACBBBCAxADBCDDACABCBCxBADxxBCADBBCBDCBCCCBCxDxxCDBDDDDDBBCCCACxACDBCxxBCAABDBCDCBCxCABCACxADCCxxBxACABAxCAxDxBDxxxCADxCADDACxDxCDCCDCDACxxABDBxCBDDCACADxDAAAADBBDCxxAAxxCBxBDCDBBDxxADAAxBCxAABDCBBDAxBCBxxDxCBxABDDDCBABxBCAABxxDBBBxDBCDxxDACBACCABDCxBACCxCBCCDCDBDCCxACBCADDBDACDBDCDDDCxxCxxCAxCDDDBAxxDDxxCDADDxAxxxDDBAAxBADxBCxBDAxCAxBAxCAxDABCADCCBDxADxBxBBxCDDBAAxBAxAAACDADBDCAACDxxDABADxBAAxxAxDCBBBxxBxxCADAAAAxxBCDCxABxCCACDDACCBADCDCBCACABBAxCABxxCADBACCCACBACxABCBxDAxBBxACCDDCxADBDCBBACxxDDxxBxCDABxxDACBxAxBBBABDDCxAxCCDxBxBBxACABCAAxBxCDxBAxxxDAABDCDBCCCBDBxCxBCBBCDCCCAxDADBCxBBCAAABxDxBAADxxAxDACDABxCxDAxBxACxDBxxxDxAxBDBAxCCCxDxBCCCBDBDCBDACCBABAACCCDBBBAxADAABDDxCBDBBxxABAAxDABDxBxxCxxDDxxBBABCxBCCDCDBDCADDBxBABDCBCACBCCADxADDBxDBAxCxBxxBxAAAxxCCxDxDCDxBDDAxDDDDxxCDxBxCDDACACDDDxBBACDBBxDxABAABCBBxDxBCCCDCAABDAACADAxDCCDxCACAAxAxAAAABxACDCBxBBBABDxDBCBBCxDCCCACBxDxDDCCACBDADCAAABxxCDCCAxDBCxAADAxDxACCACADCBAxAxBAAxCACBCCCxBDxDDBCDBDCDADDABxxBBxCCCBBDxDDxBxDxADABDBDxDBDCACxCABCADCBBBBABxDBxAAABxBxCBBCBxADAABADADACDBAABDCCBDBCxDCDxBADDDBCxCBDxCxxCBxDBADDxCDDBABDDBxCACCCDBxDDAAxADxBCBCBDCxxCCCAxBBCCDBBBCxBCDDDBDADxxxDBCCADBCABDCxDBCBDCBxDACxxAACxDDxDxDxCxxCBCCDCAAADDCACAAxxDBBCAxDxAAABCBDACxBxCxDxDAACABBDCDAAxAAxCBxDDBBADxAxAABxACxDBBxBAxxDABxDACCAABADBBxABDBAADCCxxCxADADxAxBDABBxDBBxBDDBBCCABAAABBAxBBAAxADxBAxCDxBCCBxDCDCBxDBxDACCDBBBCCDDCDAADxCxxxDxAxAADAxxDDADDDBxBCCDDxDABBCCCAxABCxBBCxDADCDCAAABDDCACAAAxADCCABAABCACDxDBCxADxADBCxAACxDADDDCxDxACCxxDAADABBBCxxxABAxCACAxADBxDxxDCBDxDCADxxDxxADCDxxCBAAxDBDBACCBAxCAAxABBDxxDADCBDCCBxDxBABAACCCBxDCxCACDACABDBDBDDDDABDAxDCDAAxBDxxxDCCDDBDxDDxCDCAxCCDBxADACBCCBDBDBDxABDACAADCxBBBCCDBADDCxAxAABCABBDCCCCBCBxCDDxAxDCADBBCxAxxxABDxAxAACxCxCxCDAACCxxBDDAAACxDCDxCBBxACBxxBxDABCAxBCCDAABAACADACCxBDxBxABBCBBCBCAxBAACBCxBAxDBCAAAACxxCCDDDDxBDBBABAxACxAAxxBDCACDCCDCxACAACCxAxxDCDDxCDBxxBCBBACBBBBCABAABxCBBxAxCDDCCDCADBABBBDxBxDAAADxAAxAAxBBxCAACCxAABDBDBCBAxxABBBCDxCCDDBCBDBxDBBCAADAADAxBCBCDBxAxBBDBDDBAAxAABDBxxCxCACCDCCDADDCxxCACDDxABABxCBDDBBDBDABCBxDBBxxDBxxDxBCxBBxCBCBACADxCACBDAAxBCDBCBxACCCACxDDCDADDxxAxxxxCADCBAAAxCxxxxCxCxACxDAACAADCDBCxDDxCxBxAABAABABBBCDDBABDACDCBxBADCxBDBADBCAxAAAABBDxCAxCCDCDxCCCxDxBACACBAAxDCADAABBDDACDAxDCxAABCxABADAABDDCCAxDDDxACBDDABCxDDxCCxADCxBBAxABAxDDDxDxCABDBxBBABBABAAxBDxDDAAAxADBDDBxDCBACDACDACBCDxxBDDCBDBxCAAxCxDxCAxBDADxCBxABCACxBCDAxxBDDCACBxBCxDAxxxxCDBDxDAABCAAxCDBDBADxxxADxACxBADxxADxADxAADDxCBBBCCxBDAxABDBxDCDBDxABDAABABCACCBDBACABxxBABDDxCDBBxBBxBCxDxBCBDAACAxxxCABxDABxACxDDxCxCxCDxADBCDBCxCxDCDBCDCADABxCBBxADADxxxCAABAxAAAxBDCDBBDACCABBBCABACDBCCBCADACBCCABxDBDAxCADxBBxABAAxADADAAxDCACDxADCABxBCCCABBCxACCxDDABDCBABAABxxxBxBCAAABBABCCCAADBAxBxBDABCADCxBBBDxBBAxCxxBBxBCBABDAADDCBxAxACBDxDxABABxBxBCxAxBCACABCDxDDCCxxxxDBCBDCBCCDBBDxBBBDBxDDACCAxCABxxCxABABCABABADABDAxADxDAxAACBxxDACABCBDDxBxACBBADxADAxCAxDxDCCBACDxBBxBBxACDDBBADBCAxDAxACBCxDDDABBDBCBBxCBAACBAABxDxCDxDxBxxDBxCxDDBCAABCDDCBBxAxCBACCxBACBDDCxACxCCBxCxDBABxDxxDxDCABDxCADCCDDCABxAxDCDCCxDxxBxBAxBBACBxxAABxxDABBDABBxCxCxABADDBxxCAAxABAxBADCADCCCxDCCCxxxxCAxDCADxDBABxxCxDCCDCCxADADDABCCDDDDCxxCxCBxAxBABCCBDBCAAADxxABCCAADxDDBxxDACDBDxxCBABACDBAAxxxACCxxCCDDxADBDBAABxBBBABxCCDCCBxxABBDxBDBxBDBDBxxABABDABDxxBCBDCCxCACCDADDDADBBBBAxDCABADxCBCCDxAAxCCACBBBxBDABxDDBCBxABADxBBxBxBxCBDADBDBxBBDDDADCCDDADDCABBBDAADxxDxBDDxxDAxCCDBDBADDDCACBCxCCCBADAxxDBCDBCDCCADxDADxDBAxxCCBxDCDDDAxxxABCCDBAABxBAABxDDDDDxCBxBCCBCDAxCBDDACxACCACABDBBBDDDxCxCBBBxBBBBABDDCDxDxDDCxBAxDxCxAxCCAADADBCCCxDABxDBCCxDBCxDBxxxABBCxDDACAxxBCDxBxCADDCDCCCxBDABDBDBBABxBCCCCxDACABCDxxBBCADxBBxBABABDDADCDxDCDxDACBCxxCBACDCBCDCADxBxABADCCCBBDCxxDBACCDxDxBAACDBCDxxDxxAAAAxDxxxABCDCxCDBxADAxABDDBDCCBABxCxDxDDxABCCDABxDCADCxCCBADDADAxBDACDxBxDCAACDADxAADCDCxDBACBAxxDAAxBCACBxBCDxACxxAxBBCxxxBCxCCBABCDxBCDABACABBxACDDBCCCxxBDCxDDBCBCDCBxACxDDxBCCBDADBxDxBCxACDDADxDxCxDBxAABDBADCBABxxxxBBBBCDCDBxADBABDxBCCxBDBACCDBxCCACxACDBDADxDAADDxABDDxACADABxDCAxDADBBxBCAxAADCxDAAxCxDCBxCBxCBDBCCDxxBxDBCABxCBCCxDBCxxxDDxACBAxxCBDAADCACABBBCAxxDxCBDxDCxCDDCDBDBDCBBDBCABxBCABCBxxBDCDDDBBxCBBCCDCAABCDAABDxxACDABBCBBAxxBBBADDCBxAADCAABADABBAxACxDBDDxBCCDCxCCBCBCBAxACxDxADDDACCBAxBxCCBBCCADAxBxBxxCxxAADADCxxDABxBxDDBABCCxCABAAACAAAxAxADBxAADBBBCBACxBADDCBxAxxADBxBBDCABxDBDAAxDDBBCxxCACxAAxACDBxACCDDDDDDCBxBxDDAACCxxxBDxCxAxCDAADDxDCDxBAADAxxADDAADBBBCDDAADBAACCAxCAxDAACDxBxABAAAxxDDxxDCCADADDxBBxCDxAxCxBAADCxAACDCADBDBCBCBAAABxCBDDABADADxxAxDxBDDBCDxxBDABDCCBDDACCBBAACAACDBACDCxABBADCxCBAxDxDBCCCBCCBxxACxAxBCDCAxCABDAxxACACDCxxBCAAAAAxDBCABxxBCDCABDBBxxBDDAxxCABAAAxBDADDCBCCDBDACDCAxCxABCABACBBACAxACxBBDxDCCCDDCBDxBBBDBxxBDxBADACxBCCBAxCBDxAAAxDxDBCxCDCADCDDBCCBBCBCCCDDAxCACCCBBBBCDCDBCADCCDxACADxCDABCAAxDACxxxAxABBBCCxAxCADDBDBxBxDBxxBDBCDAACACBCDCACABBCCDAAxADBABCBCBDDBxCAxCxABABBDAADBxCCBDDABBCCBBBxCDxCDDCCxADCxxBCDACBDxBxxBDDBxDBBBDCCABxBAxCACABxAxDABxBBxxCAxACACACDCBBAxBDDDCBBCCADCBADDDDxCBDCCxBDBxDBCACACCCADDCDxBBBAAxDDxxCAxCxAxxCxxCBBxAxCCADDDCxCBDACBxDADCDBxCxBBDxCBCxDBCDDxxAxACDxxxBDADDACCACCDCxDCBDBCBxBCBAxACCBDACDxCBBBCACBDxCAAxxAxDCBACxDCDCxDCCCDxCBDxBAxAAxCBxxBxBBAAxxBAABDAxDxDDAABCxxBBBCxCCADCAADDACDDBDBxxDCBDCCBACDCAxBBxCCCAxABCBDxAABBCAAxABDBxABDDADCxCAADCxDDABCBAxDADDCxBDBBABBCDDBAxDBxCxCBDDBACADACBDAAxBBADBAAxxDAxDDAAxDxxADxxAxBCxAxABxCDADxCAxDBBABBBxxBBAxDxDDxDBBCCBCABDADCDBABDADCABADDxABCDAxBDxCxxCDDCxCBBACCBxDDBCDDBAACAACAAACxABxACxDDCBDABDCDCDDCCxxAxxxCBDBBAxDCxABAACDDBBCABCxxxDxxxBBDCDDCABCACCDBCBCBxxxBCCCxxDxCADxBBDCCxBBxBACBDDBBxBDADBxxxCCDxDCCACCBDCDAxAxxAACABxCCBCBxCxDACBBDCDDxAADBABDCDABDBxxxADDCCDABADxDxBBDxDBDCDADAAxABBxxxxBCDBBBBBxCDxBBABBBABABDCBDDDABABxxxABxCAxAAADxABCABxCAxCxAABBABDBADCADAxADDCAxAxDxCCAxCxABDBBxCACxxDCBxxDBAABAxAABxCADDABABABxBBCACDAAAxDBCDDACxAACBxxABDBDADCADxAxxBxADBADxDCCDxBCBxxADCBABxCDxAAxxxCxBxBBBCDxAACCACCCCDBCBDxDBxxBDCxBCAABBxCCACDCACxxCCBDDAABDDDCBACxxBADDCBxADDxBBCCACACxDxBBCAxCDxAAADBxxBCABxBxCxBBBCACDDCDABDxDxDxBDCABxDBCDBCBBCAxDDDDDAAxBDBBxCDAACCCBxACCBxCBADBCADCCCBACCADBABxBABADDBCBDAxADAAxAABBCAABxDBCCDxCxCCCxDxACxBBDCBxxxCABCCADAxABADDADCxxACBBBBxCBBBCBCxDCCxACCCBxxDDCCCBDDBxABCADADCDCxABABABCCCAxDCCDCDBBCBxADxDxDxCCDBAxDDABCBABCACACAxBDDCCBDAxxDDDACAxBBAADCADACCCxDDBCCADDxCADDBxADxDDADxBABxBBADAABACDADDCBADDBBCCDACDCCAxDDDBBDCDBAAxxxDCCxCCBADDCCxxABDBDDDxAAABDxADDxxDxADBBDDAxDBDxDxDxADCADBBDBBDBAAxBBCAxDBCCBxCDBDDCABCxxBAxAxBAxDCCAACDAAxDDDCACxxxBBDAACBxCDCDDDxDBBBCACxACCxxAABCxxBADxCxCxxDxDDDABxDCBCAAxxBAxCxCDBCAABDDAxCACDBAxxCDxCCBACACBxxCDBDAxABBCACCDDBADABAAAADCxBDxxACBAxAxCDCCBCBCBBxCCBCDxBxDCDCDCBxBxCAADBDxDDDDxBDBAAAxDBxCACAxAABCDABxADDACxxBADAxxxCCxADAxDAAADDCCAACDxCDCBABAxCDBBDBDDCAABDDCBxxBCDDBCCBCxCBABCxCACCBDDACDxDACCCBBAAACABBDBDBCxDxBCBCAxCBDCDAxxBDxDxDCxBAACxBAAxABDBABADBBDACDDAxBACxCCxxBCxBABDBDDxBAxxxABCBBCxAADADABBAxxAAxADxADxBCxABDxDADDDACxxxBCBCCADDxDADBxADAxCxxx"

fun main() {
    println(countPotions(testInput))
    println(countPotions(input))
    println(countPotions2(testInput2))
    println(countPotions2(input2))
    println(countPotions3(testInput3))
    println(countPotions3(input3))

}

val monsterScores = mapOf('B' to 1, 'C' to 3, 'D' to 5)

fun countPotions(monsters: String): Int {
    return monsters.sumOf { monsterScores.getOrDefault(it, 0) }
}

fun countPotions2(monsters: String): Int {
    return monsters.chunked(2).sumOf { score3(it) }
}

fun countPotions3(monsters: String): Int {
    return monsters.chunked(3).sumOf { score3(it) }
}

fun score3(s: String): Int {
    val xCount = s.count { it == 'x' }
    val sc = s.sumOf { monsterScores.getOrDefault(it, 0) }
    val result = sc + when (xCount) {
        0 -> 6
        1 -> 2
        else -> 0
    }
    return result
}