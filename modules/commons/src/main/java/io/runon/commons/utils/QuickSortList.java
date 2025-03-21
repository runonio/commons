

package io.runon.commons.utils;

import java.util.LinkedList;
import java.util.List;


public class QuickSortList {


	@SuppressWarnings("rawtypes")
	private List list;
//	private double [][] num;
	
	/**
	 * 생성자
	 * 아무것도 하지않음
	 * @param list
	 */
	public QuickSortList(@SuppressWarnings("rawtypes") List list){
		if(list.size() <2){
			return;
		}
		this.list = list;
	}
	
	/**
	 * 생성자
	 * 생성과 동시에 정렬 내림차순
	 * @param list
	 * @param num
	 */
	public QuickSortList(@SuppressWarnings("rawtypes") List list, double [] num){
		
		if(list.size() <2){
			return;
		}
		this.list = list;
		quicksortDesc(num, 0, num.length-1);
	}
	
	/**
	 * 생성자 생성과 동시에 정렬
	 * @param list
	 * @param num
	 * @param isAsc
	 */
	public QuickSortList(@SuppressWarnings("rawtypes") List list, double [] num, boolean isAsc){
		if(list.size() <2){
			return;
		}
		this.list = list;
		if(isAsc){
			quicksortAsc(num, 0, num.length-1);
		}else{
			quicksortDesc(num, 0, num.length-1);
			
		}
	}
	
	/**
	 * 생성자
	 * @param list
	 * @param num
	 * @param isAsc
	 */
	public QuickSortList(@SuppressWarnings("rawtypes") List list, int [] num, boolean isAsc){
		if(list.size() <2){
			return;
		}
		this.list = list;
		if(isAsc){
			quicksortAsc(num, 0, num.length-1);
		}else{
			quicksortDesc(num, 0, num.length-1);
			
		}
	}
	
	/**
	 * 생성자
	 * 생성과 동시 정렬 내림차순
	 * @param list
	 * @param num
	 */
	public QuickSortList(@SuppressWarnings("rawtypes") List list, int [] num){
		if(list.size() <2){
			return;
		}
		this.list = list;
		quicksortDesc(num, 0, num.length-1);
	}
	
	/**
	 * 생성자
	 * @param list
	 * @param num
	 * @param lower
	 * @param hight
	 */
	public QuickSortList(@SuppressWarnings("rawtypes") List list, int [] num, int lower, int hight){
		if(list.size() <2){
			return;
		}
		this.list = list;
		quicksortDesc(num, lower , hight);
	}
	
	
	/**
	 * 내림차순 정렬 (double형)
	 * @param num
	 */
	public void sortDesc(double [] num){
		quicksortDesc(num, 0, num.length-1);
	}
	
	/**
	 * 내림차순 정렬 (double형) 정렬범위 지정 
	 * @param num
	 * @param lower
	 * @param hight 
	 */
	public void sortDesc(double [] num, int lower, int hight){
		quicksortDesc(num, lower, hight);
	}
	
	/**
	 * 오름차순 정렬 (int 형)
	 * @param num
	 */
	public void sortAsc(int [] num){
		quicksortAsc(num, 0, num.length-1);
	}
	/**
	 * 오름차순 정렬 (int 형) 정렬범위 지정
	 * @param num
	 * @param lower
	 * @param hight
	 */
	public void sortAsc(int [] num, int lower, int hight){
		quicksortAsc(num, lower, hight);
	}
	/**
	 * 내림차순 정렬( int형)
	 * @param num
	 */
	public void sortDesc(int [] num){
		quicksortDesc(num, 0, num.length-1);
	}
	
	/**
	 * 내림차순 정렬( int형) 정렬범위 지정
	 * @param num
	 * @param lower
	 * @param hight
	 */
	public void sortDesc(int [] num, int lower, int hight){
		quicksortDesc(num, lower, hight);
	}

	@SuppressWarnings("unchecked")
	private  void insert(int x,int y){
		Object xData = (Object) list.get(x);
		
		list.add(y,xData);
		list.remove(y+1);
	} 
	@SuppressWarnings("unchecked")
	private  void insert(Object pivotData ,int y){
		list.add(y,pivotData);
		list.remove(y+1);
	}
	private void quicksortDesc (int [] a,  int lo, int hi){
	    int i=lo, j=hi, h;
	    int x=a[(lo+hi)/2];
	    Object temp;
	    do
	    {    
	        while (a[i]>x) i++; 
	        while (a[j]<x) j--;
	        if (i<=j)
	        {
	            h=a[i]; 
	            temp = list.get(i); 
	            a[i]=a[j]; 
	            insert(j,i);
	            a[j]=h;
	            insert(temp,j);
	            
	            i++; j--;
	        }
	    } while (i<=j);

	
	    if (lo<j) quicksortDesc(a, lo, j);
	    if (i<hi) quicksortDesc(a, i, hi);
	    
	}
	
	//내림차순정렬
	private void quicksortDesc (double [] a,  int lo, int hi){
	    int i=lo, j=hi;
	    double h;
	    double x=a[(lo+hi)/2];
	    Object temp;
	    do
	    {    
	        while (a[i]>x) i++; 
	        while (a[j]<x) j--;
	        if (i<=j)
	        {
	            h=a[i]; 
	            temp = list.get(i); 
	            a[i]=a[j]; 
	            insert(j,i);
	            a[j]=h;
	            insert(temp,j);
	            
	            i++; j--;
	        }
	    } while (i<=j);

	    if (lo<j) quicksortDesc(a, lo, j);
	    if (i<hi) quicksortDesc(a, i, hi);
	}
	
	
	//오름차순정렬
	public  void quicksortAsc(int[] a, int lo, int hi)
	{
		// BASE CASE
		if (lo >= hi)
			// lo has equalled (or crossed) hi, so we have
			// either a singleton or an empty array, either
			// way, it is sorted
			return;
		// first, set the pivot to the first element
		int pivot = a[lo];
		// now, keep pivoting until the left and right indexes cross
		int leftIndex = lo + 1;
		int rightIndex = hi;
		while (rightIndex > leftIndex)
		{
			// 1. if the element on the right is in the right place,
			// move the rightIndex one to the left
			if (a[rightIndex] > pivot)
				rightIndex--;
			
			// 2. else if the element on the left is in the right place,
			// move the leftIndex one to the right
			else if (a[leftIndex] <= pivot)
				leftIndex++;
			
			// 3. otherwise, both sides are wrong, so SWAP them
			else
				swap(a,leftIndex,rightIndex);
		}
		// at this point, leftIndex and rightIndex equal each
		// other, and the pivot is stored in a[lo].
		// We now want to swap the pivot (a[0]) with the last
		// element on the left.  This will either be the element
		// where the two indexes met, or the one just to its left.
		int newPivotIndex;
		if (a[leftIndex] > pivot)
			newPivotIndex = leftIndex - 1;
		else
			newPivotIndex = leftIndex;
		// now just swap the pivot into its new location
		swap(a,lo,newPivotIndex);
		// now recursively sort the left and right sides, being
		// sure not to sort the pivot again!
		quicksortAsc(a,lo,newPivotIndex-1);
		quicksortAsc(a,newPivotIndex+1,hi);
	}
	
	public  void swap(int[] a, int i, int j)
	{
		int temp = a[i];
		Object tempOjbect = list.get(i);  
		a[i] = a[j];
		insert(j,i);
		a[j] = temp;
        insert(tempOjbect,j);
//        h=a[i]; 
//        temp = list.get(i); 
//        a[i]=a[j]; 
//        insert(j,i);
//        a[j]=h;
//        insert(temp,j);
	}

	
	
	
	
	
	//=====

	public  void quicksortAsc(double[] a, int lo, int hi)
	{
		// BASE CASE
		if (lo >= hi)
			// lo has equalled (or crossed) hi, so we have
			// either a singleton or an empty array, either
			// way, it is sorted
			return;
		// first, set the pivot to the first element
		double pivot = a[lo];
		// now, keep pivoting until the left and right indexes cross
		int leftIndex = lo + 1;
		int rightIndex = hi;
		while (rightIndex > leftIndex)
		{
			// 1. if the element on the right is in the right place,
			// move the rightIndex one to the left
			if (a[rightIndex] > pivot)
				rightIndex--;
			
			// 2. else if the element on the left is in the right place,
			// move the leftIndex one to the right
			else if (a[leftIndex] <= pivot)
				leftIndex++;
			
			// 3. otherwise, both sides are wrong, so SWAP them
			else
				swap(a,leftIndex,rightIndex);
		}
		// at this point, leftIndex and rightIndex equal each
		// other, and the pivot is stored in a[lo].
		// We now want to swap the pivot (a[0]) with the last
		// element on the left.  This will either be the element
		// where the two indexes met, or the one just to its left.
		int newPivotIndex;
		if (a[leftIndex] > pivot)
			newPivotIndex = leftIndex - 1;
		else
			newPivotIndex = leftIndex;
		// now just swap the pivot into its new location
		swap(a,lo,newPivotIndex);
		// now recursively sort the left and right sides, being
		// sure not to sort the pivot again!
		quicksortAsc(a,lo,newPivotIndex-1);
		quicksortAsc(a,newPivotIndex+1,hi);
	}
	
	
	
	public  void swap(double[] a, int i, int j)
	{
		double temp = a[i];
		Object tempOjbect = list.get(i);  
		a[i] = a[j];
		insert(j,i);
		a[j] = temp;
        insert(tempOjbect,j);
//        h=a[i]; 
//        temp = list.get(i); 
//        a[i]=a[j]; 
//        insert(j,i);
//        a[j]=h;
//        insert(temp,j);
	}

	
	public static void main(String [] args){
		int []  dafafsa = {5,2,3,4,1,7,8,6};
//		quicksortAsc(dafafsa,0,dafafsa.length-1);
		
		List<Integer> aa =new LinkedList<Integer>();
		for(int dd: dafafsa){
			aa.add(dd);
		}
//		for(int i=0;i<aa.size();i++){
//			System.out.println(aa.get(i));
//		}
		
		new QuickSortList(aa, dafafsa, false);
		
		for(int i=0;i<aa.size();i++){
			System.out.println(aa.get(i));
		}
		
	}
	
}