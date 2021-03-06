/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.oracle.graal.lir.hsail;

import static com.oracle.graal.api.code.ValueUtil.*;
import static com.oracle.graal.lir.LIRInstruction.OperandFlag.*;

import com.oracle.graal.lir.*;
import com.oracle.graal.api.code.*;
import com.oracle.graal.api.meta.*;
import com.oracle.graal.asm.hsail.*;

/**
 * Represents an address value used in HSAIL code.
 */
public final class HSAILAddressValue extends CompositeValue {

    private static final long serialVersionUID = 1802222435353022623L;
    @Component({REG, LIRInstruction.OperandFlag.ILLEGAL}) private AllocatableValue base;
    private final long displacement;

    /**
     * Creates an {@link HSAILAddressValue} with given base register and no displacement.
     * 
     * @param kind the kind of the value being addressed
     * @param base the base register
     */
    public HSAILAddressValue(Kind kind, AllocatableValue base) {
        this(kind, base, 0);
    }

    /**
     * Creates an {@link HSAILAddressValue} with given base register and a displacement. This is the
     * most general constructor.
     * 
     * @param kind the kind of the value being addressed
     * @param base the base register
     * @param displacement the displacement
     */
    public HSAILAddressValue(Kind kind, AllocatableValue base, long displacement) {
        super(kind);
        this.base = base;
        this.displacement = displacement;
        assert !isStackSlot(base);
    }

    public HSAILAddress toAddress() {
        Register baseReg = base == Value.ILLEGAL ? Register.None : asRegister(base);
        return new HSAILAddress(baseReg, displacement);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getKind().getJavaName()).append("[");
        String sep = "";
        if (isLegal(base)) {
            s.append(base);
            sep = " + ";
        }
        if (displacement < 0) {
            s.append(" - ").append(-displacement);
        } else if (displacement > 0) {
            s.append(sep).append(displacement);
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HSAILAddressValue) {
            HSAILAddressValue addr = (HSAILAddressValue) obj;
            return getKind() == addr.getKind() && displacement == addr.displacement && base.equals(addr.base);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return base.hashCode() ^ ((int) displacement << 4) ^ (getKind().ordinal() << 12);
    }
}
