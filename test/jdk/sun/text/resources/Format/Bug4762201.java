/*
 * Copyright (c) 2007, 2023, Oracle and/or its affiliates. All rights reserved.
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

/*
 * @test
 * @bug 4762201 8174269
 * @modules jdk.localedata
 * @summary verify the zh_CN full time pattern (and other time patterns)
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Bug4762201
{
        public static void main(String[] arg)
        {
                int result = 0;
                Locale loc = Locale.of("zh","CN");
                Date now = new Date();

                DateFormat df =
                   DateFormat.getTimeInstance(DateFormat.SHORT,loc);
                SimpleDateFormat sdf = new SimpleDateFormat("",loc);
                sdf.applyPattern("HH:mm");                              // short time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;
                df =  DateFormat.getTimeInstance(DateFormat.MEDIUM,loc);
                sdf.applyPattern("HH:mm:ss");                           // medium time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;
                df = DateFormat.getTimeInstance(DateFormat.LONG,loc);
                sdf.applyPattern("z HH:mm:ss");                         // long time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;
                df = DateFormat.getTimeInstance(DateFormat.FULL,loc);
                sdf.applyPattern("zzzz HH:mm:ss");                      // full time pattern
                if( !sdf.format(now).equals(df.format(now))) result++;

           if(result > 0) throw new RuntimeException();
        }
}
