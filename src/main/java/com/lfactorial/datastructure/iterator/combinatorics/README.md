CombinationIterator

    For a list of length n and combination size r,
    The total possible combinatino is C(n, r) which is ( n! / (r!(n-r)!))
    So for the string "abcd" and combination size 2, the total number of combination string output is
    4!/(2! 2!) = (2x3x4)/(2x2) = 6
    If we arrange them in lexicographical order : ab ac ad bc bd cd 
    The code guarantees the lexicographical order of the next item of the iterator if the input string is sorted.



PermutationIteartor
    
    For a list of length n ,
    The total possible permutation is n!
    So for the string "abc" the total number of permutation string output is
    3! =  6
    If we arrange them in lexicographical order : abc acb bac bca cab aba
    The code guarantees the lexicographical order of the next item of the iterator if the input string is sorted.
 
