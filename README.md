# SubsetSum

SubsetSum - parallel statistical algorithm for solving of subset sum problem

In computer science, the subset sum problem is an important problem in 
complexity theory. The problem is this: from a given set of natural numbers 
want to select elements whose sum is a given natural number. The problem is 
NP-complete.

The proposed algorithm is faster to find subset, if the arithmetic mean 
of the initial set close to the average of the desired subset. 
The algorithm sorts the set in ascending order of deviations from the mean, 
and creates computational processes for enumerating k-combinations. 
Processes are created in ascending order of deviations from the average size 
of the desired subset. Combinations are enumerated in the order, prefer 
a subsets with the most average items. 

The existence of solution is checked by Dynamic Programming.
