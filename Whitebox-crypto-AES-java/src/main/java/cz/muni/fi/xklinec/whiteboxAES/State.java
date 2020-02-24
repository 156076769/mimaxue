/*
 * Copyright (c) 2014, Dusan (Ph4r05) Klinec, Petr Svenda
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the copyright holders nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package cz.muni.fi.xklinec.whiteboxAES;

import java.io.Serializable;
import java.util.Arrays;

/**
 * AES-128 State
 * @author ph4r05
 */
public class State implements Serializable, Copyable{
    public static final int BYTES = 16;
    public static final int ROWS  = 4;
    public static final int COLS  = BYTES / ROWS;
    protected byte[] state;
    protected boolean immutable=false;

    /**
     * Default constructor, allocates memory for internal state representation.
     */
    public State() {
        init();
    }

    /**
     * Uses given byte[] as internal representation, no copy.
     * @param state 
     */
    public State(byte[] state) {
        this.state = state;
    }
    
    /**
     * Copy/assign constructor.
     * @param state
     * @param copy 
     */
    public State(byte[] state, boolean copy) {
        if (copy){
            this.state = Arrays.copyOf(state, BYTES);
        } else {
            this.state = state;
        }
    }
    
    /**
     * Copy/assign constructor. Can transpose input bytes - AES input state
     * is loaded by columns.
     * 
     * @param state
     * @param copy 
     * @param transpose    transpose, copy is forced
     */
    public State(byte[] state, boolean copy, boolean transpose) {
        if (transpose==false){
            if (copy){
                this.state = Arrays.copyOf(state, BYTES);
            } else {
                this.state = state;
            }
        } else {
            init();
            for(int i=0; i<BYTES; i++){
                this.state[i] = state[transpose(i)];
            }
        }
    }
    
    /**
     * Sets whole vector to zero.
     */
    public void zero(){
        Arrays.fill(this.state, (byte)0);
    }
    
    /**
     * Per-byte state getter.
     * @param idx
     * @return 
     */
    public byte get(int idx){
        if (idx<0 || idx >= BYTES){
            throw new IllegalArgumentException("Invalid byte requested");
        }
        
        return this.state[idx];
    }
    
    /**
     * Per-byte state setter.
     * @param b
     * @param idx 
     */
    public void set(byte b, int idx){
        if (idx<0 || idx >= BYTES) {
            throw new IllegalArgumentException("Invalid byte requested");
        }
        
        if (state == null){
            throw new NullPointerException("State is not initialized");
        }
        
        if (immutable){
            throw new IllegalAccessError("State is set as immutable, cannot change");
        }
        
        this.state[idx] = b;
    }
    
    /**
     * Returns index to byte array for 2D coordinates, indexed by rows (0 1 2 3)
     * @param i
     * @param j
     * @return 
     */
    public static int getIdx(int i, int j){
        return i*COLS + j;
    }
    
    /**
     * Returns index to byte array for 2D coordinates, indexed by cols (0 4 8 12)
     * @param i
     * @param j
     * @return 
     */
    public static int getCIdx(int i, int j){
        return j*ROWS + i;
    }
    
    /**
     * Returns transposed index for matrix 
     *  00 01 02 03        00 04 08 12
     *  04 05 06 07        01 05 09 13
     *  08 09 10 11  --->  02 06 10 14
     *  12 13 14 15        03 07 11 15
     * 
     * @param idx
     * @return 
     */
    public static int getTIdx(int idx){
        return getCIdx(idx / COLS, idx % ROWS);//  4*((idx)%4) + ((idx)/4);
    }
    
    /**
     * Transpose 4x4 index for state matrix.
     * 
     * @param idx
     * @return 
     */
    public static int transpose(int idx){
        return (idx / COLS) + ROWS * (idx % ROWS);
    }
    
    /**
     * Getter for 2D coordinates, assuming first line indexing: 0 1 2 3
     * @param i row
     * @param j column
     * @return 
     */
    public byte get(int i, int j){
        return get(getIdx(i, j));
    }
    
    /**
     * Getter for 2D coordinates, assuming first line indexing: 0 4 8 12
     * @param i row
     * @param j column
     * @return 
     */
    public byte getC(int i, int j){
        return get(getCIdx(i, j));
    }
    
    /**
     * Getter for transposed index
     * @param i row
     * @param j column
     * @return 
     */
    public byte getT(int idx){
        return get(getTIdx(idx));
    }
    
    /**
     * Getter for 2D coordinates, assuming first line indexing: 0 1 2 3
     * @param i row
     * @param j column
     * @return 
     */
    public void set(byte b, int i, int j){
        set(b, getIdx(i, j));
    }
    
    /**
     * Getter for 2D coordinates, assuming first line indexing: 0 4 8 12
     * @param i row
     * @param j column
     * @return 
     */
    public void setC(byte b, int i, int j){
        set(b, getCIdx(i, j));
    }
    
    /**
     * Getter for transposed index
     * @param i row
     * @param j column
     * @return 
     */
    public void setT(byte b, int idx){
        set(b, getTIdx(idx));
    }
    
    /**
     * Sets column from 32bit type.
     * Assumes we have 4 rows (32bit type).
     * @param col
     * @param idx 
     */
    public void setColumn(W32b col, int idx){
        final byte[] c = col.get();
        state[idx+ 0] = c[0];
        state[idx+ 4] = c[1];
        state[idx+ 8] = c[2];
        state[idx+12] = c[3];
    }
    
    /**
     * State initialization - memory allocation
     */
    public final void init(){
        if (immutable){
            throw new IllegalAccessError("State is set as immutable, cannot change");
        }
        
        state = new byte[BYTES];
    }
    
    /**
     * State initialization - memory allocation
     */
    public static byte[] initExt(){
        return new byte[BYTES];
    }

    /**
     * Whole state getter.
     * 
     * WARNING, if this object is set immutable, it should return copy of an array,
     * but from performance reasons it is not the case here. 
     * 
     * @return 
     */
    public byte[] getState() {
        return state;
    }
    
    /**
     * Whole state getter. 
     * Returns copy of internal representation.
     * 
     * @return 
     */
    public byte[] getStateCopy() {
        return Arrays.copyOf(state, BYTES);
    }

    /**
     * Whole state setter, copy
     * @param state 
     */
    public void setState(final byte[] state) {
        this.setState(state, true);
    }
    
    /**
     * State setter with optional copy.
     * Copy is done via Arrays.copy, so new memory is allocated.
     * 
     * @param state
     * @param copy 
     */
    public void setState(final byte[] state, boolean copy) {
        if (state.length != BYTES) {
            throw new IllegalArgumentException("XOR table has to have 8 sub-tables");
        }
        
        if (immutable){
            throw new IllegalAccessError("State is set as immutable, cannot change");
        }
        
        if (copy){
            this.state = Arrays.copyOf(state, BYTES);
        } else {
            this.state = state;
        }
    }   
    
    /**
     * Loads state data from source to currently allocated memory.
     * @param src 
     */
    public void loadFrom(final State src){
        if (immutable){
            throw new IllegalAccessError("State is set as immutable, cannot change");
        }
        
        System.arraycopy(src.getState(), 0, this.state, 0, BYTES);
    }

    /**
     * Deep copy of objects
     * 
     * @param src
     * @param dst 
     */
    public static void copy(final State src, State dst){
        dst.setState(dst.getState(), true);
    }
    
    /**
     * Returns deep copy of state.
     * 
     * @return 
     */
    public Copyable copy() {
        return new State(this.getState(), true);
    }

    public boolean isImmutable() {
        return immutable;
    }

    public void setImmutable(boolean immutable) {
        this.immutable = immutable;
    }
    
    /**
     * Transposes this state.
     * Returns this state for fluent interface.
     */
    public State transpose(){
        byte[] tmp = new byte[BYTES];
        for(int i=0; i<BYTES; i++){
            tmp[i] = this.getT(i);
        }
        
        this.state = tmp;
        return this;
    }
    
    /**
     * Transposes this state.
     * Returns new state that is transposed copy of this state.
     */
    public static State getTranspose(State s){
        byte[] tmp = new byte[BYTES];
        for(int i=0; i<BYTES; i++){
            tmp[i] = s.getT(i);
        }
        
        return new State(tmp);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Arrays.hashCode(this.state);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Arrays.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (state==null){
            return "State{state=null}";
        }
        
        StringBuilder sb = new StringBuilder();
        final int ln = state.length;
        for(int i=0; i<ln; i++){
            sb.append(String.format("0x%02X", state[i] & 0xff));
            if ((i+1)!=ln){
                sb.append(", ");
            }
        }
        
        return "State{" + "state=" + sb.toString() + ";mem="+state+"}";
    }
}
