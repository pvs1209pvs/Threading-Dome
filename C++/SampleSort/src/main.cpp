#include <iostream>
#include <algorithm>
#include <cstdlib>
#include <array>
#include <iterator>
#include <vector>
#include <random>
#include <chrono> 
#include "omp.h" 

int const N = 40000;
int const P = 4;

void sort_subset(int * arr, int thrd_id);

void pick_splitters(int * arr, std::vector<int> &s, int thrd_id);

std::vector<int> splitter_selection(std::vector<int> splitter, int max_ele);

std::vector<int> make_buckets(int * arr, std::vector<int> glbl_splitter, int thrd_id);

int main(){

    int arr[N];
    for (int i = 0; i < N; i++){
        arr[i] = i+1;
    }

    std::shuffle(arr, arr+N, std::default_random_engine(std::chrono::system_clock::now().time_since_epoch().count()));

    std::vector<std::vector<int>> sorted{};

    #pragma omp parallel num_threads(P)
    for (int i = 0; i < P; ++i){
        sort_subset(arr, i);
    }

    std::vector<int> splitters{};

    #pragma omp parallel num_threads(P)
    for (int i = 0; i < P; i++){
        pick_splitters(arr, splitters, i);  
    }

    sort(splitters.begin(), splitters.end());

    std::vector<int> global_splitter = splitter_selection(splitters, *std::max_element(arr, arr+N)+1);

    #pragma omp parallel num_threads(P)
    for (int i = 0; i < P; i++){
        sorted.push_back(make_buckets(arr, global_splitter, i));  
    }

    return 0;

}

void sort_subset(int * arr, int thrd_id){
    int start = thrd_id*N/P;
    int end = start + N/P;
    std::sort(arr+start, arr+end);
}

void pick_splitters(int * arr, std::vector<int>& s, int thrd_id){

    int start = thrd_id*N/P;
    int end = start + N/P;
    int shift = (end-start)/P;

    for (int i = start+shift; i < end; i+=shift+1){
        s.push_back(arr[i]);
    }

}

std::vector<int> splitter_selection(std::vector<int> splitter, int max_ele){

    std::vector<int> buckets{};

    buckets.push_back(0);

    int jump = splitter.size()/P;

    for (int i = jump; i < splitter.size(); i+=jump+1){
        buckets.push_back(splitter.at(i-1));
    }

    buckets.push_back(max_ele);

    return buckets;
    
}

std::vector<int> make_buckets(int * arr, std::vector<int> glbl_splitter, int thrd_id){

    std::vector<int> temp{};

    for (int i = 0; i < N; i++){
        if(arr[i] >= glbl_splitter[thrd_id] && arr[i] < glbl_splitter[thrd_id+1]){
            temp.push_back(arr[i]);

        }
    }

    std::sort(temp.begin(), temp.end());

    return temp;
    
}