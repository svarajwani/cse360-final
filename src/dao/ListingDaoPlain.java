package dao;

import models.Listing;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ListingDaoPlain {
    private static final Path FILE = Paths.get("data", "listings.txt");


    public List<Listing> findByCategory(String cat) {
        if (!Files.exists(FILE)) return List.of();

        List<Listing> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(FILE)) {
            br.readLine(); // skip header
            for (String row; (row = br.readLine()) != null; ) {
                if (row.isBlank() || row.startsWith("#")) continue;
                String[] f = row.split("\\|", -1);
                if (f.length < 10) continue;                     // malformed line
                if (!cat.equals(f[5]) || Integer.parseInt(f[9]) == 0) continue;
                list.add(map(f));
            }
        } catch (IOException e) { throw new UncheckedIOException(e); }

        list.sort(Comparator
                .comparingInt((Listing l) -> condOrder(l.cond()))
                .thenComparing(Listing::title));
        return list;
    }


    public synchronized boolean purchase(String listingId) {
        try {
            List<String> lines = Files.readAllLines(FILE);
            for (int i = 1; i < lines.size(); i++) {          // skip header
                String[] f = lines.get(i).split("\\|", -1);
                if (!listingId.equals(f[0])) continue;
                int qty = Integer.parseInt(f[9]);
                if (qty == 0) return false;
                f[9] = String.valueOf(qty - 1);
                lines.set(i, String.join("|", f));
                Files.write(FILE, lines);                     // overwrite
                return true;
            }
        } catch (IOException e) { throw new UncheckedIOException(e); }
        return false;
    }

    /* ── helpers ─────────────────────────────────────────── */
    private Listing map(String[] f) {                         // 10 fields
        return new Listing(
                f[0],f[1],f[2],f[3],
                Integer.parseInt(f[4]),f[5],f[6],
                Double.parseDouble(f[7]),
                Double.parseDouble(f[8]),
                Integer.parseInt(f[9]));
    }
    private int condOrder(String c) {
        return switch (c) {
            case "Used-Like-New"   -> 1;
            case "Moderately-Used" -> 2;
            default                -> 3;   // Heavily-Used
        };
    }
}
