/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rymate.SimpleWarp;

import org.bukkit.Location;
import org.bukkit.World;

import java.io.*;
import java.util.*;

/**
 * @author rymate
 */
public class WarpFileHandler {

    public final String FILE_WARPS = "warps.txt";
    public File configFile; // = new File(m_Folder + File.separator + "config.properties");
    public HashMap<String, Location> m_warps = new HashMap();
    private final SimpleWarpPlugin plugin;
    public File warpFile = new File("plugins/SimpleWarp/warps.txt");


    public WarpFileHandler(SimpleWarpPlugin warpPlugin) {
        this.plugin = warpPlugin;

    }


    //Start configuration stuff

    public boolean saveSettings() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.warpFile));
            for (Map.Entry entry : this.m_warps.entrySet()) {
                Location loc = (Location) entry.getValue();
                if (loc != null) {
                    writer.write((String) entry.getKey() + "," + loc.getX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "," + loc.getPitch() + "," + loc.getYaw() + "," + loc.getWorld().getName());
                    writer.newLine();
                }
            }
            writer.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    public boolean loadSettings() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.warpFile));
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(",");
                if (values.length == 7) {
                    double X = Double.parseDouble(values[1]);
                    double Y = Double.parseDouble(values[2]);
                    double Z = Double.parseDouble(values[3]);
                    float pitch = Float.parseFloat(values[4]);
                    float yaw = Float.parseFloat(values[5]);

                    World world = plugin.getServer().getWorld(values[6]);
                    if (world != null) {
                        this.m_warps.put(values[0], new Location(world, X, Y, Z, yaw, pitch));
                    }
                }
                line = reader.readLine();
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    public String[] getList(String name) {
        Scanner scanner;
        List<String> list = new ArrayList<String>();
        try {
            scanner = new Scanner(new FileReader(this.warpFile));

            try {

                // first use a Scanner to get each line
                int i = 0;
                while (scanner.hasNextLine()) {

                    String[] elements = scanner.nextLine().split(",");

                    if (elements[0].matches(".*" + name + ".*")) {
                        list.add(elements[0]);
                        i++;
                    }

                    if (i >= 8) {
                        break;
                    }

                }

            } catch (Exception e) {
                System.out.println("Warp:" + e.getMessage());
                e.printStackTrace();
            } finally {

                scanner.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Warp:" + e.getMessage());
        }

        String[] locations = new String[list.size()];
        list.toArray(locations);

        return locations;
    }


    //end Configuration stuff
}
