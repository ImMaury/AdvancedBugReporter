package me.im_maury.bugreporter.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Map;

public class JsonManager {

    private JSONArray root;
    private File jsonFile;

    public JsonManager(String jsonPath) {
        this.jsonFile = new File(jsonPath);
        this.root = new JSONArray();
        load();
    }

    private void load() {
        try (FileReader reader = new FileReader(jsonFile)) {
            if (jsonFile.length() != 0) {
                root = (JSONArray) new JSONParser().parse(reader);
            }
        } catch (FileNotFoundException e) {
            try {
                jsonFile.getParentFile().mkdirs();
                jsonFile.createNewFile();
                save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getRoot() {
        return root;
    }

    public JSONObject[] getSuperObjects() {
        JSONObject[] superObjects = new JSONObject[root.size()];
        int i = 0;
        for (Object obj : root) {
            superObjects[i] = (JSONObject) obj;
            i++;
        }
        return superObjects;
    }

    public JSONObject getById(String id) {
        for (JSONObject obj : getSuperObjects()) {
            if (obj.get("id").equals(id)) return obj;
        }
        return null;
    }

    public JSONArray getStaffer(String id) {
        for (JSONObject obj : getSuperObjects()) {
            if (obj.get("id").equals(id)) {
                return (JSONArray) obj.get("haveseen");
            }
        }
        return null;
    }

    public Location getLocation(String id) {
        if (getById(id) == null) return null;
        World world = Bukkit.getWorld(((JSONObject) getById(id).get("coordinates")).get("world").toString());
        double x = Double.parseDouble(((JSONObject) getById(id).get("coordinates")).get("x").toString());
        double y = Double.parseDouble(((JSONObject) getById(id).get("coordinates")).get("y").toString());
        double z = Double.parseDouble(((JSONObject) getById(id).get("coordinates")).get("z").toString());
        return new Location(world, x, y, z);
    }

    public void addStaffer(String id, String stafferName) {
        for (JSONObject obj : getSuperObjects()) {
            if (obj.get("id").equals(id)) {
                ((JSONArray) obj.get("haveseen")).add(stafferName);
                save();
            }
        }
    }

    public JSONObject newJSONObject(Map<String, String> map) {
        JSONObject newObj = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            newObj.put(entry.getKey(), entry.getValue());
        }
        return newObj;
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
            Gson prettifiedJson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(root.toJSONString());
            writer.write(prettifiedJson.toJson(je));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
