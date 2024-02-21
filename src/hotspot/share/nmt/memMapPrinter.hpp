/*
 * Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023, Red Hat, Inc. and/or its affiliates.
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
 *
 */

#ifndef SHARE_SERVICES_MEMMAPPRINTER_HPP
#define SHARE_SERVICES_MEMMAPPRINTER_HPP

#include "memory/allocation.hpp"
#include "memory/allStatic.hpp"
#include "utilities/globalDefinitions.hpp"

#ifdef LINUX

class outputStream;
class CachedNMTInformation;

class MappingPrintSession {
  outputStream* const _out;
  const bool _detail_mode;
  const bool _print_only_summary;
  const CachedNMTInformation& _nmt_info;
public:
  MappingPrintSession(outputStream* st, const CachedNMTInformation& nmt_info,
                      bool detail_mode, bool print_only_summary);
  void print_nmt_info_for_region(const void* from, const void* to) const;
  void print_nmt_flag_legend(int indent) const;
  outputStream* out() const { return _out; }
  bool print_only_summary() const { return _print_only_summary; }
  bool detail_mode() const { return _detail_mode; }
};

class MemMapPrinter : public AllStatic {
  static void pd_print_all_mappings(MappingPrintSession& session);
public:
  static void print_all_mappings(outputStream* st, bool summary_only);
};

#endif // LINUX

#endif // SHARE_SERVICES_MEMMAPPRINTER_HPP
