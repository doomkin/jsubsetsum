/**
 * Class BitMatrix implements bit matrix using BitVector.
 * 
 * @author Pavel Nikitin
 * License: MIT
 */
package com.github.doomkin.jsubsetsum;

public class BitMatrix 
{
    private BitVector[] _matrix;
    private int _rows, _cols;
    
    public BitMatrix(int rows, int cols) 
    {
        _rows = rows;
        _cols = cols;        

        // create matrix
        _matrix = new BitVector[rows];        
        for (int row = 0; row < rows; row++)
            _matrix[row] = new BitVector(cols);
    }

    public BitMatrix(BitMatrix a) 
    {
        _rows = a.rows();
        _cols = a.cols();
        
        // create matrix
        _matrix = new BitVector[_rows];
        for (int row = 0; row < _rows; row++)
            _matrix[row] = new BitVector(a.getRow(row));
    }

    public int rows() 
    { 
        return _rows; 
    }
    
    public int cols() 
    { 
        return _cols; 
    }
    
    public boolean get(int row, int col) 
    {
        return _matrix[row].get(col);
    }
    
    public BitVector getRow(int row) 
    {
        return _matrix[row];
    }
    
    public BitVector getCol(int col) 
    {
        BitVector result = new BitVector(_rows);
        
        for (int row = 0; row < _rows; row++)
            result.set(row, _matrix[row].get(col));
        
        return result;
    }
    
    public void set(int row, int col, boolean value) 
    {
        _matrix[row].set(col, value);
    }

    public void set(int row, int col) 
    {
        _matrix[row].set(col);
    }

    public void swapRows(int row1, int row2) 
    {
        BitVector swap = _matrix[row1];    
        _matrix[row1] = _matrix[row2]; 
        _matrix[row2] = swap;
    }
    
    public void xorRow(int row, BitVector v) 
    {
        _matrix[row].xor(v);
    }
    
    public void andRow(int row, BitVector v) 
    {
        _matrix[row].and(v);
    }
    
    public String toString() 
    {
        String result = "";
        
        for (int row = 0; row < _rows; row++)
            result += _matrix[row] + "\n";
        
        return result;
    }
}
