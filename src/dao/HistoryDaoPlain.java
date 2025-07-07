package dao;

import models.Listing;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;

public class HistoryDaoPlain {
    private static final Path SALES  = Paths.get("data", "sales.txt");
    private static final Path PAYOUT = Paths.get("data", "payouts.txt");

    /** create files with header if they don't exist */
    public static void ensureFiles() throws IOException {
        createIfAbsent(SALES ,
                "# id|buyer|title|category|paid|timestamp");
        createIfAbsent(PAYOUT,
                "# id|seller|title|category|earned|timestamp");
    }

    /** log both buyer-side and seller-side records */
    public void logSaleAndPayout(String buyer, Listing l) throws IOException {
        String ts   = LocalDateTime.now().toString();
        String sale = join("S", buyer , l.title(), l.cat(), l.sysPrice(), ts);
        String pay  = join("P", l.seller(), l.title(), l.cat(), l.sysPrice() * 0.85, ts);

        append(SALES , sale);
        append(PAYOUT, pay );
    }

    /* ── helpers ─────────────────────────────────────────── */
    private static void createIfAbsent(Path p, String header) throws IOException {
        if (!Files.exists(p)) {
            Files.createDirectories(p.getParent());
            Files.writeString(p, header + System.lineSeparator());
        }
    }

    private void append(Path p, String line) throws IOException {
        Files.writeString(p, line + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private String join(String prefix, String a, String b, String c, double d, String ts) {
        return String.join("|", id(prefix), a, b, c, fmt(d), ts);
    }

    private String id(String pre) {
        return pre + "-" + System.currentTimeMillis();
    }

    private String fmt(double v) {
        return String.format("%.2f", v);
    }
}
