/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
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
package com.oracle.graal.graph.iterators;

import java.util.*;

import com.oracle.graal.graph.*;

public class PredicatedProxyNodeIterator<T extends Node> extends NodeIterator<T> {
    private final Iterator<T> iterator;
    private final NodePredicate predicate;
    private final NodePredicate until;
    public PredicatedProxyNodeIterator(NodePredicate until, Iterator<T> iterator, NodePredicate predicate) {
        this.until = until;
        this.iterator = iterator;
        this.predicate = predicate;
    }
    @Override
    protected void forward() {
        while ((current == null || !current.isAlive() || !predicate.apply(current)) && iterator.hasNext()) {
            current = iterator.next();
        }
        if (current != null && (!current.isAlive() || !predicate.apply(current) || until.apply(current))) {
            current = null;
        }
    }
}
