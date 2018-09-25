package cc.peaks.androidtestingbible.retrofit_okhttp;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

/**
 * An entity representing GitHub's repository
 *
 * @author Fumihiko Shiroyama (fu.shiroyama@gmail.com)
 */

public class Repo {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("private")
    private boolean isPrivate;

    @SerializedName("owner")
    private Owner owner;

    public Repo() {
    }

    public Repo(long id, String name, String fullName, boolean isPrivate, Owner owner) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.isPrivate = isPrivate;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", htmlUrl='" + htmlUrl + '\'' +
                ", isPrivate=" + isPrivate +
                ", owner=" + owner +
                '}';
    }

    public static class Owner {
        @SerializedName("id")
        private long id;

        @SerializedName("login")
        private String login;

        @SerializedName("avatar_url")
        private String avatarUrl;

        @SerializedName("url")
        private String url;

        public Owner() {
        }

        @VisibleForTesting(otherwise = VisibleForTesting.NONE)
        public Owner(long id, String login, String avatarUrl, String url) {
            this.id = id;
            this.login = login;
            this.avatarUrl = avatarUrl;
            this.url = url;
        }

        public long getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Owner{" +
                    "id=" + id +
                    ", login='" + login + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
