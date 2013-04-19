/*
 * Copyright (c) 2009-2012, toby weston & tempus-fugit committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.astute.jcip.concurrency;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Deadlocks extends OutputStream {

    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    @Override
    public void write(int b) throws IOException {
        stream.write(b);
    }

    public Boolean detected() {
        return stream.toString().length() > 0;
    }

    @Override
    public String toString() {
        if (detected())
            return stream.toString();
        return "no deadlock detected";
    }
}
