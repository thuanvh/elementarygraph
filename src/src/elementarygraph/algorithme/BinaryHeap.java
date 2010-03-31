/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package elementarygraph.algorithme;

import java.util.Vector;

/**
 *
 * @author donnghi
 */
public class BinaryHeap {
    
    private Vector data;

    /**
     * Construct the binary heap.
     */
    public BinaryHeap( ) {
        data = new Vector();
    }
    
    /**
     * Construct the binary heap from an array.
     * @param vectorData items the inital items in the binary heap.
     */
    public BinaryHeap( Vector vectorData ) {
        
        data = new Vector();
        for ( int i = 0; i <= vectorData.size(); i++ ) 
            data.add(new Vector());
        for( int i = 0; i < vectorData.size(); i++ ) {
            ((Vector) data.get(i + 1)).add( getValueVector(vectorData, i, 0) );
            ((Vector) data.get(i + 1)).add( getValueVector(vectorData, i, 1) );
        }
        buildHeap( );
    }

    //************************************XU LY VECTOR****************************//
    /**
     * Ham se tra ve doi tuong Objet tai vi tri xac dinh cua vector
     * @param vectorData vector luu tru data
     * @param i gia tri hang`
     * @param j gia tri cot
     * @return Objet la gia tri luu tai hang i, cot j
     */
    public static Object getValueVector( Vector vectorData, int i, int j ) {
        return ((Vector) vectorData.get(i)).elementAt(j);
    }
    
    public static void assignValueVector( Vector vectorData, int i, int j, Object data ) {
        if (((Vector) vectorData.get(i)).size() > j)
            ((Vector) vectorData.get(i)).remove(j);
        ((Vector) vectorData.get(i)).add(data);
    }
    
    public static int getIndexVector( Vector vectorData, String name ) {
        for ( int i = 0; i < vectorData.size() ; i++ )
            if ( ((String) getValueVector( vectorData, i, 0 )).equalsIgnoreCase(name))
                return i;
        return -1;
    }
    
    public static Integer convertToInt( String str ) {
        Integer temp = 0;
        for ( int i = 0;i < str.length(); i++ ) {
            temp = temp*10 + str.charAt(i) - '0';
        }
        return temp;
    }
    
    public static void addValueVector( Vector vectorData, int i, int j, int value) {
        Integer temp = (Integer) getValueVector( vectorData, i, j );
        
        temp = temp + value;
        assignValueVector( vectorData, i, j, (Object) temp);
    }
    
    //Tra ve danh sach cac dinh nam sau dinh co' ten la` name
    //Du lieu lay tu data goc' (data lay tu` file xml)
    public static Vector getListVector( Vector vectorData, String name ) {
        Vector tmp = new Vector();
        int index = getIndexVector( vectorData, name );
        for ( int j = 2; j < ((Vector)vectorData.get(index)).size() ; j++) {
            tmp.add(((Vector)vectorData.get(index)).get(j));
        }
        return tmp;
    }
    
    //************************************XU LY VECTOR****************************//
    
    public int getSize() {
        return data.size() - 1;
    }
    
    public int getValue( int i ) {
        return ((Integer)getValueVector( data, i, 1)).intValue();
    }
    
    public String getName( int i ) {
        
        return (String) getValueVector( data, i, 0 );
    }
    
    public Vector findMin() {
        return (Vector)data.get(1);
    }
    
    public void downHeap( int ind ) {
        while ( true ) {
            int left = ind * 2;
            int minIndex;
            if ( left < data.size() )
                minIndex = left;
            else
                break;
            if ( left + 1 < data.size() ) {
                if ( compareVector(left + 1, left ) )
                    minIndex = left + 1;
            }
            if ( compareVector( minIndex, ind )) {
                Vector tmp = new Vector();
                tmp.add((Vector)data.get(minIndex));
                data.setElementAt((Vector)data.get(ind), minIndex);
                data.setElementAt(tmp.get(0), ind);
                ind = minIndex;
            }
            else break;
        }
        
    }
    
    public void upHeap( int ind ) {
        data.setElementAt((Vector)data.get(ind), 0);
        while ( true ) {
            int parent = ind/2;
            if ( compareVector( ind, parent )) {
                Vector tmp = new Vector();
                tmp.add((Vector)data.get(parent));
                data.setElementAt((Vector)data.get(ind), parent);
                data.setElementAt(tmp.get(0), ind);
                ind = parent;
            }
            else break;
        }
    }
    
    public void deleteMin() {
        
        data.setElementAt((Vector)data.get(data.size() - 1), 1);
        data.remove( data.size() - 1 );
        
        downHeap( 1 );
        
    }
    
    public void decrement( int index ) {
        addValueVector( data, index, 1, -1);
        upHeap( index );
    }
    
    public void decrement( String name ) {
        int index = getIndex( name );
        decrement( index );
    }
    
    private int getIndex( String name ) {
        for ( int i = 1; i < data.size() ; i++ )
            if ( getName( i ).equalsIgnoreCase(name) )
                return i;
        return -1;
    }
    
    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( ) {
        for( int i = data.size() / 2; i > 0; i-- )
            downHeap( i );
    }

//    
//    /**
//     * Internal method to percolate down in the heap.
//     * @param hole the index at which the percolate begins.
//     */
//    private void percolateDown( int hole ) {
//        int child;
//        int tmp = ((Integer) getValueVector(data, hole, 1)).intValue();
//        
//        for( ; hole * 2 < data.size(); hole = child ) {
//            child = hole * 2;
//            if( child != data.size() && 
//                    compareVector( child + 1, child ) )
//                child++;
//            if( ((Integer) getValueVector(data, child, 1)).intValue() < tmp )
//                assignValueVector( data, hole, 1, ((Vector) data.get( child )).get( 1 ) );                
//            else
//                break;
//        }
//        Integer temp = tmp;
//        assignValueVector( data, hole, 1, (Object)temp );
//    }

    /**
     * Ham nay se so sanh hai vi tri va tra ve true neu gia tri tai i < gia tri tai j
     * @param i
     * @param j
     * @return
     */
    private boolean compareVector( int i, int j ) {
        return (((Integer) getValueVector(data, i, 1)).compareTo(((Integer) getValueVector(data, j, 1))) < 0);
    }

}
