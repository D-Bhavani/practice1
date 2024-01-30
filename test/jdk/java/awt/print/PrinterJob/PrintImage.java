/*
 * Copyright (c) 2007, 2024, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import jtreg.SkippedException;

/*
 * @test %I %W
 * @bug 4298489
 * @summary Confirm that output is same as screen.
 * @key printer
 * @library /java/awt/regtesthelpers
 * @library /test/lib
 * @build PassFailJFrame
 * @build jtreg.SkippedException
 * @run main/manual PrintImage
 */
public class PrintImage extends Frame implements ActionListener {

    private PrintImageCanvas printImageCanvas;

    private final MenuItem print1Menu = new MenuItem("PrintTest1");
    private final MenuItem print2Menu = new MenuItem("PrintTest2");
    private final MenuItem exitMenu = new MenuItem("Exit");

    private static final String INSTRUCTIONS =
            "You must have a printer available to perform this test,\n" +
            "prefererably Canon LaserShot A309GII.\n" +
            "Printing must be done in Win 98 Japanese 2nd Edition.\n" +
            "\n" +
            "Passing test : Output of text image for PrintTest1 and PrintTest2 should be " +
            "same as that on the screen.";

    public static void main(String[] argv) throws Exception {

        if (PrinterJob.lookupPrintServices().length == 0) {
            throw new SkippedException("Printer not configured or available."
                    + " Test cannot continue.");
        }

        PassFailJFrame.builder()
                .instructions(INSTRUCTIONS)
                .testUI(PrintImage::new)
                .rows((int) INSTRUCTIONS.lines().count() + 1)
                .columns(45)
                .build()
                .awaitAndCheck();
    }

    public PrintImage() {
        super("PrintImage");
        initPrintImage();
    }

    public void initPrintImage() {

        printImageCanvas = new PrintImageCanvas(this);

        initMenu();

        setLayout(new BorderLayout());
        add(printImageCanvas, BorderLayout.CENTER);
        pack();

        setSize(500, 500);
    }

    private void initMenu() {
        MenuBar mb = new MenuBar();
        Menu me = new Menu("File");
        me.add(print1Menu);
        me.add(print2Menu);
        me.add("-");
        me.add(exitMenu);
        mb.add(me);
        this.setMenuBar(mb);

        print1Menu.addActionListener(this);
        print2Menu.addActionListener(this);
        exitMenu.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target.equals(print1Menu)) {
            printMain1();
        } else if (target.equals(print2Menu)) {
            printMain2();
        } else if (target.equals(exitMenu)) {
            dispose();
        }
    }

    private void printMain1() {

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();

        printerJob.setPrintable(printImageCanvas, pageFormat);

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        } else
            printerJob.cancel();
    }

    private void printMain2() {

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.pageDialog(printerJob.defaultPage());

        printerJob.setPrintable(printImageCanvas, pageFormat);

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        } else
            printerJob.cancel();
    }
}

class PrintImageCanvas extends Canvas implements Printable {

    public PrintImageCanvas(PrintImage pds) {
    }

    public void paint(Graphics g) {
        Font drawFont = new Font("MS Mincho", Font.ITALIC, 50);
        g.setFont(drawFont);
        g.drawString("PrintSample!", 100, 150);
    }

    public int print(Graphics g, PageFormat pf, int pi)
            throws PrinterException {

        if (pi >= 1)
            return NO_SUCH_PAGE;
        else {
            g.setColor(new Color(0, 0, 0, 200));

            Font drawFont = new Font("MS Mincho", Font.ITALIC, 50);
            g.setFont(drawFont);
            g.drawString("PrintSample!", 100, 150);
            return PAGE_EXISTS;
        }
    }
}
