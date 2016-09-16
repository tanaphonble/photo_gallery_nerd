package ayp.aug.photogallerynerd;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tanaphon on 8/20/2016.
 */
public class FlickrFetchr {

    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "c4a6c2b53e5f0cef1e1fcf4ae4308ade";
    private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final Uri ENDPOINT = Uri
            .parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
            .build();


    /**
     * parse JSON Using GSON
     *
     * @param jsonBody JSON object form flickr api
     */
    private void parseItemsV2(List<GalleryItem> items, JSONObject jsonBody)
            throws IOException, JSONException {

        String jsonString = jsonBody.toString();

        Gson gson = new GsonBuilder().create();
        Flickr flickr = gson.fromJson(jsonString, Flickr.class);
        for (Photo p : flickr.photos.photo) {
            GalleryItem item = new GalleryItem();
            item.setId(p.id);
            item.setCaption(p.title);
            if (p.url_s == null)
                continue;
            item.setUrl(p.url_s);
            items.add(item);
        }
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
            throws IOException, JSONException {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));

            if (!photoJsonObject.has("url_s"))
                continue;

            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);
        }
    }

    /**
     * Fetch JsonBody from flickr
     */
    private List<GalleryItem> downloadGalleryItems(String url) {

        List<GalleryItem> items = new ArrayList<>();

        try {

            String jsonString = getUrlString(url);

            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
//            parseItems(items, jsonBody);
            parseItemsV2(items, jsonBody);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
            ioe.printStackTrace();
        }
        return items;
    }

    public List<GalleryItem> fetchRecentPhotos(){
        String url = buildUrl(FETCH_RECENTS_METHOD, null);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(String query){
        String url = buildUrl(SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }

    private String buildUrl(String method, String query){
        Uri.Builder builder = ENDPOINT.buildUpon()
                .appendQueryParameter("method", method);
        if(method.equals(SEARCH_METHOD)) {
            builder.appendQueryParameter("text", query);
        }
        return builder.build().toString();
    }

    /**
     * Fetches raw data from a url and return it as an array of bytes
     *
     * @return bytes from this http connection
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);

            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0)
                out.write(buffer, 0, bytesRead);

            out.close();

            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Converts the result from getUrlBytes(String) to a String
     *
     * @return String of bytes array from url
     */
    public String getUrlString(String urlSpec) throws IOException {
        Log.d(TAG, "getUrlString: ");
        return new String(getUrlBytes(urlSpec));
    }
}
