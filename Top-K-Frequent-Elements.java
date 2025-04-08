## Problem1: Top K Frequently Repeating Elements(https://leetcode.com/problems/top-k-frequent-elements/)

In this problem we have to give top K frequently occuring elements
Approach : what we will do is we will maintain a hashmap with key as a element and a value as a frequency and then we will use a priority queue
i.e min heap to find out the top K frequent elements

Time Complexity : O(n) for hashmap and O(n log k) for min heap
Space Complexity : O(n)

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        if(nums==null || nums.length ==0){
            return new int[]{};
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        //Min heap which will store elements based on the frequency
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            pq.add(new int[]{entry.getKey(),entry.getValue()});
            if(pq.size() > k){
                pq.poll();
             }
        }
        // at the end we are adding elements in result array but at the top we will have a element with less frequency as it is a min heap 
        // so we will add that element at the end and then go to start so that the element at the start will be of highest frequency
        int[] result = new int[k];
        int index =k;
        for(int i=0;i<k;i++){
            int[] polled = pq.poll();
            result[index-1]= polled[0];
            index--;
        }
        return result;
    }
}

//The above approach was having a time complexity of O(n logk) because of the heap what if we don't use heap and instead of that use
// an array of lists of size maxfrequency+1 so why list because we can have multiple elements with same frequency so we will have lists where
// index of an array will be the frequency so if 3 is maxfrequency then index will be 0 to 2 but we want 3 so maxfrequency+1

TC:O(n)
SC:O(n)

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        if(nums==null || nums.length ==0){
            return new int[]{};
        }
        int maxFreq = 0;
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num : nums){
            map.put(num,map.getOrDefault(num,0)+1);
            maxFreq = Math.max(maxFreq,map.get(num));
        }
        List<Integer>[] list = new ArrayList[maxFreq+1];
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if(list[entry.getValue()]==null){
                list[entry.getValue()]=new ArrayList<>();
            }
            list[entry.getValue()].add(entry.getKey());
        }
        int index = maxFreq;
        int i=0;
        int[] result = new int[k];
        for(int j=index;j>0;j--){
            if(list[j] != null){
                List<Integer> elements = list[j];
                while(k>0 && elements.size()>0){
                    result[i] = elements.remove(elements.size()-1);
                    i++;
                    k--;
                    if(k==0){
                        break;
                    }
                }        
            }
            else{
                continue;
            }
        }
        return result;
    }
}