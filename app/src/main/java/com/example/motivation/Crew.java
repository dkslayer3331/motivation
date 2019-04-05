package com.example.motivation;

import com.google.gson.annotations.SerializedName;

public class Crew {
    @SerializedName("department")
    private String department;

    @SerializedName("job")
    private String job;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profile_url;

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfile_url() {
        return profile_url;
    }
}
