package inc.appscode0.actionbound;

import java.util.HashMap;

/**
 * Created by jjmomanyis on 6/7/17.
 */

public class BoundsData {


    public String bound_name;
    public String bound_description;
    public String bound_category;
    public String bounds_length;
    public String bounds_duration;
    public String bound_id;
    public HashMap<String, String> datadata;
    public String url;

    public String getPlay_mode() {
        return play_mode;
    }

    public void setPlay_mode(String play_mode) {
        this.play_mode = play_mode;
    }



    public String play_mode ;



    public String getBound_name() {
        return bound_name;
    }

    public void setBound_name(String bound_name) {
        this.bound_name = bound_name;
    }

    public String getBound_description() {
        return bound_description;
    }

    public void setBound_description(String bound_description) {
        this.bound_description = bound_description;
    }

    public String getBound_category() {
        return bound_category;
    }

    public void setBound_category(String bound_category) {
        this.bound_category = bound_category;
    }

    public String getBounds_length() {
        return bounds_length;
    }

    public void setBounds_length(String bounds_length) {
        this.bounds_length = bounds_length;
    }

    public String getBounds_duration() {
        return bounds_duration;
    }

    public void setBounds_duration(String bounds_duration) {
        this.bounds_duration = bounds_duration;
    }

    public String getBound_id() {
        return bound_id;
    }

    public void setBound_id(String bound_id) {
        this.bound_id = bound_id;
    }

    public HashMap<String, String> getDatadata() {
        return datadata;
    }

    public void setDatadata(HashMap<String, String> datadata) {
        this.datadata = datadata;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public BoundsData(String bound_name, String bound_description, String bound_category, String bounds_length, String bounds_duration, String bound_id, HashMap<String, String> datadata, String url, String play_mode) {
        this.bound_name = bound_name;
        this.bound_description = bound_description;
        this.bound_category = bound_category;
        this.bounds_length = bounds_length;
        this.bounds_duration = bounds_duration;
        this.bound_id = bound_id;
        this.datadata = datadata;
        this.url = url;
        this.play_mode=play_mode;
    }


}