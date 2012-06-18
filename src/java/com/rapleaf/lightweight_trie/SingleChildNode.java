/**
 *  Copyright 2011 Rapleaf
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.rapleaf.lightweight_trie;

import java.util.Set;

final class SingleChildNode<V> extends AbstractNode<V> {
  private final char[] prefix;
  private final AbstractNode<V> child;

  protected SingleChildNode(char[] prefix, V value, AbstractNode<V> child) {
    super(value);
    this.prefix = prefix;
    this.child = child;
  }

  @Override
  public AbstractNode<V>[] getChildren() {
    return new AbstractNode[]{child};
  }

  @Override
  public char[] getPrefix() {
    return prefix;
  }

  @Override
  public V get(char[] toInsert, int startOffset) {
    int commonLength = Utils.getCommonLength(toInsert, startOffset, child.getPrefix());
    if (commonLength == child.getPrefix().length) {
      if (toInsert.length == commonLength + startOffset) {
        return child.value;
      } else {
        return child.get(toInsert, startOffset + commonLength);
      }
    }
    return null;
  }

  @Override
  public void getPartialMatches(Set<String> partialMatches, char[] searchArr, int searchArrOffset) {
    int commonLength = Utils.getCommonLength(searchArr, searchArrOffset, child.getPrefix());
    if (commonLength == child.getPrefix().length) {
      partialMatches.add(new String(searchArr, 0, searchArrOffset + commonLength));
      child.getPartialMatches(partialMatches, searchArr, searchArrOffset + commonLength);
    }
  }
}
