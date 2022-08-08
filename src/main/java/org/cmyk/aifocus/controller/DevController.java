package org.cmyk.aifocus.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.annotations.SerializedName;
import com.mysql.cj.util.StringUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.aspect.AutoLogAspect;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.UserService;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RestController
@Hidden
@Slf4j
public class DevController {

    @Resource
    private RedisLockRegistry redisLockRegistry;

    private int num = 20;
    @Resource
    private UserService userService;

    /**
     * 发送POST请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     */
    public static void sendPost(String url, Map<String, String> parameters) {
        StringBuilder result = new StringBuilder();// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuilder sb = new StringBuilder();// 处理请求参数
        String params;// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 测试redis分布式锁(有锁)
     */
    @GetMapping("/testLock")
    public void testLock() throws InterruptedException {
        Lock lock = redisLockRegistry.obtain("lock");
        boolean isLock = lock.tryLock(1, TimeUnit.SECONDS);
        String s = Thread.currentThread().getName();
        if (num > 0 && isLock) {
            System.out.println(s + "排号成功，号码是：" + num);
            num--;
        } else {
            System.out.println(s + "排号失败,号码已经被抢光");
        }
        lock.unlock();
    }

    @PostMapping("/testupdate/{email}")
    public Boolean update(@RequestParam String name, @RequestParam String phone, @PathVariable String email) {
        User user = userService.getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        user.setName(StringUtils.isNullOrEmpty(name) ? null : name);
        user.setPhone(StringUtils.isNullOrEmpty(phone) ? null : phone);
        return user.updateById();
    }

    @GetMapping("/whichHost")
    public String whichHost() {
        return AutoLogAspect.getLocalHostName();
    }

//    @RequestMapping(value = "/gitee", method = RequestMethod.POST)
//    public String test2(HttpServletRequest request) throws IOException {
//        List<String> urls = new ArrayList<>();
//        urls.add("https://iyuu.cn/" + "IYUU843Tfd673008d3ea03a8c44ea68b453e2a3c21654b5d" + ".send");
//        urls.add("https://iyuu.cn/" + "IYUU1161T122d16c48e2e37cf09406aafdff8b6fa4a5fce00" + ".send");
//        urls.add("https://iyuu.cn/" + "IYUU1162T5b53c003620cedb5bfbf1d997f69a4ef25442a1e" + ".send");
//        urls.add("https://iyuu.cn/" + "IYUU1163Te73ca52ba63d222c73ab25346576cb37fe2bfdb4" + ".send");
//        Map<String, String> param = new HashMap<>();
//        BufferedReader reader = request.getReader();
//        Gson gson = new Gson();
//        GiteeJson giteeJson = gson.fromJson(reader, GiteeJson.class);
//        StringBuilder description = new StringBuilder();
//        int i = 1;
//        Collections.reverse(giteeJson.commits);
//        for (GiteeJson.CommitsBean commit : giteeJson.commits) {
//            description
//                    .append("第")
//                    .append(i++)
//                    .append("次提交\n")
//                    .append(commit.message)
//                    .append("\n提交者: ")
//                    .append(commit.author.name)
//                    .append("\n\n");
//        }
//
//        param.put("text", giteeJson.repository.name + " 有更新了");
//        param.put("desp", description.toString());
//
//        urls.forEach(url -> sendPost(url, param));
//        return "{\"status\":\"good\"}";
//    }

    @RequestMapping("/test3")
    public List<User> test3() {
        log.info("test3");
        return userService.list();
    }

    public static class GiteeJson {
        /**
         * hook_name : push_hooks
         * password : pwd
         * hook_id : 1
         * hook_url : http://gitee.com/liwen/gitos/hooks/1/edit
         * timestamp : 1576754827988
         * sign : rLEHLuZRIQHuTPeXMib9Czoq9dVXO4TsQcmQQHtjXHA=
         * ref : refs/heads/change_commitlint_config
         * before : 0000000000000000000000000000000000000000
         * after : 1cdcd819599cbb4099289dbbec762452f006cb40
         * created : true
         * deleted : false
         * compare : https://gitee.com/oschina/gitee/compare/0000000000000000000000000000000000000000...1cdcd819599cbb4099289dbbec762452f006cb40
         * commits : [{"id":"1cdcd819599cbb4099289dbbec762452f006cb40","tree_id":"db78f3594ec0683f5d857ef731df0d860f14f2b2","distinct":true,"message":"Update README.md","timestamp":"2018-02-05T23:46:46+08:00","url":"https://gitee.com/oschina/gitee/commit/1cdcd819599cbb4099289dbbec762452f006cb40","author":{"time":"2018-02-05T23:46:46+08:00","name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"},"committer":{"name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"},"added":null,"removed":null,"modified":["README.md"]}]
         * head_commit : {"id":"1cdcd819599cbb4099289dbbec762452f006cb40","tree_id":"db78f3594ec0683f5d857ef731df0d860f14f2b2","distinct":true,"message":"Update README.md","timestamp":"2018-02-05T23:46:46+08:00","url":"https://gitee.com/oschina/gitee/commit/1cdcd819599cbb4099289dbbec762452f006cb40","author":{"time":"2018-02-05T23:46:46+08:00","name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"},"committer":{"name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"},"added":null,"removed":null,"modified":["README.md"]}
         * total_commits_count : 0
         * commits_more_than_ten : false
         * repository : {"id":120249025,"name":"Gitee","path":"gitee","full_name":"开源中国/Gitee","owner":{"id":1,"login":"robot","avatar_url":"https://gitee.com/assets/favicon.ico","html_url":"https://gitee.com/robot","type":"User","site_admin":false,"name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"},"private":false,"html_url":"https://gitee.com/oschina/gitee","url":"https://gitee.com/oschina/gitee","description":"","fork":false,"created_at":"2018-02-05T23:46:46+08:00","updated_at":"2018-02-05T23:46:46+08:00","pushed_at":"2018-02-05T23:46:46+08:00","git_url":"git://gitee.com:oschina/gitee.git","ssh_url":"git@gitee.com:oschina/gitee.git","clone_url":"https://gitee.com/oschina/gitee.git","svn_url":"svn://gitee.com/oschina/gitee","git_http_url":"https://gitee.com/oschina/gitee.git","git_ssh_url":"git@gitee.com:oschina/gitee.git","git_svn_url":"svn://gitee.com/oschina/gitee","homepage":null,"stargazers_count":11,"watchers_count":12,"forks_count":0,"language":"ruby","has_issues":true,"has_wiki":true,"has_pages":false,"license":null,"open_issues_count":0,"default_branch":"master","namespace":"oschina","name_with_namespace":"开源中国/Gitee","path_with_namespace":"oschina/gitee"}
         * sender : {"id":1,"login":"robot","avatar_url":"https://gitee.com/assets/favicon.ico","html_url":"https://gitee.com/robot","type":"User","site_admin":false,"name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"}
         * enterprise : {"name":"开源中国","url":"https://gitee.com/oschina"}
         */
        private String hook_name;
        private String password;
        private int hook_id;
        private String hook_url;
        private String timestamp;
        private String sign;
        private String ref;
        private String before;
        private String after;
        private boolean created;
        private boolean deleted;
        private String compare;
        private HeadCommitBean head_commit;
        private int total_commits_count;
        private boolean commits_more_than_ten;
        private RepositoryBean repository;
        private SenderBean sender;
        private EnterpriseBean enterprise;
        private List<CommitsBean> commits;

        public String getHook_name() {
            return hook_name;
        }

        public void setHook_name(String hook_name) {
            this.hook_name = hook_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getHook_id() {
            return hook_id;
        }

        public void setHook_id(int hook_id) {
            this.hook_id = hook_id;
        }

        public String getHook_url() {
            return hook_url;
        }

        public void setHook_url(String hook_url) {
            this.hook_url = hook_url;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public boolean isCreated() {
            return created;
        }

        public void setCreated(boolean created) {
            this.created = created;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getCompare() {
            return compare;
        }

        public void setCompare(String compare) {
            this.compare = compare;
        }

        public HeadCommitBean getHead_commit() {
            return head_commit;
        }

        public void setHead_commit(HeadCommitBean head_commit) {
            this.head_commit = head_commit;
        }

        public int getTotal_commits_count() {
            return total_commits_count;
        }

        public void setTotal_commits_count(int total_commits_count) {
            this.total_commits_count = total_commits_count;
        }

        public boolean isCommits_more_than_ten() {
            return commits_more_than_ten;
        }

        public void setCommits_more_than_ten(boolean commits_more_than_ten) {
            this.commits_more_than_ten = commits_more_than_ten;
        }

        public RepositoryBean getRepository() {
            return repository;
        }

        public void setRepository(RepositoryBean repository) {
            this.repository = repository;
        }

        public SenderBean getSender() {
            return sender;
        }

        public void setSender(SenderBean sender) {
            this.sender = sender;
        }

        public EnterpriseBean getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(EnterpriseBean enterprise) {
            this.enterprise = enterprise;
        }

        public List<CommitsBean> getCommits() {
            return commits;
        }

        public void setCommits(List<CommitsBean> commits) {
            this.commits = commits;
        }

        public static class HeadCommitBean {
            /**
             * id : 1cdcd819599cbb4099289dbbec762452f006cb40
             * tree_id : db78f3594ec0683f5d857ef731df0d860f14f2b2
             * distinct : true
             * message : Update README.md
             * timestamp : 2018-02-05T23:46:46+08:00
             * url : https://gitee.com/oschina/gitee/commit/1cdcd819599cbb4099289dbbec762452f006cb40
             * author : {"time":"2018-02-05T23:46:46+08:00","name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"}
             * committer : {"name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"}
             * added : null
             * removed : null
             * modified : ["README.md"]
             */

            private String id;
            private String tree_id;
            private boolean distinct;
            private String message;
            private String timestamp;
            private String url;
            private AuthorBean author;
            private CommitterBean committer;
            private Object added;
            private Object removed;
            private List<String> modified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTree_id() {
                return tree_id;
            }

            public void setTree_id(String tree_id) {
                this.tree_id = tree_id;
            }

            public boolean isDistinct() {
                return distinct;
            }

            public void setDistinct(boolean distinct) {
                this.distinct = distinct;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public AuthorBean getAuthor() {
                return author;
            }

            public void setAuthor(AuthorBean author) {
                this.author = author;
            }

            public CommitterBean getCommitter() {
                return committer;
            }

            public void setCommitter(CommitterBean committer) {
                this.committer = committer;
            }

            public Object getAdded() {
                return added;
            }

            public void setAdded(Object added) {
                this.added = added;
            }

            public Object getRemoved() {
                return removed;
            }

            public void setRemoved(Object removed) {
                this.removed = removed;
            }

            public List<String> getModified() {
                return modified;
            }

            public void setModified(List<String> modified) {
                this.modified = modified;
            }

            public static class AuthorBean {
                /**
                 * time : 2018-02-05T23:46:46+08:00
                 * name : robot
                 * email : robot@gitee.com
                 * username : robot
                 * user_name : robot
                 * url : https://gitee.com/robot
                 */

                private String time;
                private String name;
                private String email;
                private String username;
                private String user_name;
                private String url;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class CommitterBean {
                /**
                 * name : robot
                 * email : robot@gitee.com
                 * username : robot
                 * user_name : robot
                 * url : https://gitee.com/robot
                 */

                private String name;
                private String email;
                private String username;
                private String user_name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class RepositoryBean {
            /**
             * id : 120249025
             * name : Gitee
             * path : gitee
             * full_name : 开源中国/Gitee
             * owner : {"id":1,"login":"robot","avatar_url":"https://gitee.com/assets/favicon.ico","html_url":"https://gitee.com/robot","type":"User","site_admin":false,"name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"}
             * private : false
             * html_url : https://gitee.com/oschina/gitee
             * url : https://gitee.com/oschina/gitee
             * description :
             * fork : false
             * created_at : 2018-02-05T23:46:46+08:00
             * updated_at : 2018-02-05T23:46:46+08:00
             * pushed_at : 2018-02-05T23:46:46+08:00
             * git_url : git://gitee.com:oschina/gitee.git
             * ssh_url : git@gitee.com:oschina/gitee.git
             * clone_url : https://gitee.com/oschina/gitee.git
             * svn_url : svn://gitee.com/oschina/gitee
             * git_http_url : https://gitee.com/oschina/gitee.git
             * git_ssh_url : git@gitee.com:oschina/gitee.git
             * git_svn_url : svn://gitee.com/oschina/gitee
             * homepage : null
             * stargazers_count : 11
             * watchers_count : 12
             * forks_count : 0
             * language : ruby
             * has_issues : true
             * has_wiki : true
             * has_pages : false
             * license : null
             * open_issues_count : 0
             * default_branch : master
             * namespace : oschina
             * name_with_namespace : 开源中国/Gitee
             * path_with_namespace : oschina/gitee
             */

            private int id;
            private String name;
            private String path;
            private String full_name;
            private OwnerBean owner;
            @SerializedName("private")
            private boolean privateX;
            private String html_url;
            private String url;
            private String description;
            private boolean fork;
            private String created_at;
            private String updated_at;
            private String pushed_at;
            private String git_url;
            private String ssh_url;
            private String clone_url;
            private String svn_url;
            private String git_http_url;
            private String git_ssh_url;
            private String git_svn_url;
            private Object homepage;
            private int stargazers_count;
            private int watchers_count;
            private int forks_count;
            private String language;
            private boolean has_issues;
            private boolean has_wiki;
            private boolean has_pages;
            private Object license;
            private int open_issues_count;
            private String default_branch;
            private String namespace;
            private String name_with_namespace;
            private String path_with_namespace;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getFull_name() {
                return full_name;
            }

            public void setFull_name(String full_name) {
                this.full_name = full_name;
            }

            public OwnerBean getOwner() {
                return owner;
            }

            public void setOwner(OwnerBean owner) {
                this.owner = owner;
            }

            public boolean isPrivateX() {
                return privateX;
            }

            public void setPrivateX(boolean privateX) {
                this.privateX = privateX;
            }

            public String getHtml_url() {
                return html_url;
            }

            public void setHtml_url(String html_url) {
                this.html_url = html_url;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isFork() {
                return fork;
            }

            public void setFork(boolean fork) {
                this.fork = fork;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getPushed_at() {
                return pushed_at;
            }

            public void setPushed_at(String pushed_at) {
                this.pushed_at = pushed_at;
            }

            public String getGit_url() {
                return git_url;
            }

            public void setGit_url(String git_url) {
                this.git_url = git_url;
            }

            public String getSsh_url() {
                return ssh_url;
            }

            public void setSsh_url(String ssh_url) {
                this.ssh_url = ssh_url;
            }

            public String getClone_url() {
                return clone_url;
            }

            public void setClone_url(String clone_url) {
                this.clone_url = clone_url;
            }

            public String getSvn_url() {
                return svn_url;
            }

            public void setSvn_url(String svn_url) {
                this.svn_url = svn_url;
            }

            public String getGit_http_url() {
                return git_http_url;
            }

            public void setGit_http_url(String git_http_url) {
                this.git_http_url = git_http_url;
            }

            public String getGit_ssh_url() {
                return git_ssh_url;
            }

            public void setGit_ssh_url(String git_ssh_url) {
                this.git_ssh_url = git_ssh_url;
            }

            public String getGit_svn_url() {
                return git_svn_url;
            }

            public void setGit_svn_url(String git_svn_url) {
                this.git_svn_url = git_svn_url;
            }

            public Object getHomepage() {
                return homepage;
            }

            public void setHomepage(Object homepage) {
                this.homepage = homepage;
            }

            public int getStargazers_count() {
                return stargazers_count;
            }

            public void setStargazers_count(int stargazers_count) {
                this.stargazers_count = stargazers_count;
            }

            public int getWatchers_count() {
                return watchers_count;
            }

            public void setWatchers_count(int watchers_count) {
                this.watchers_count = watchers_count;
            }

            public int getForks_count() {
                return forks_count;
            }

            public void setForks_count(int forks_count) {
                this.forks_count = forks_count;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public boolean isHas_issues() {
                return has_issues;
            }

            public void setHas_issues(boolean has_issues) {
                this.has_issues = has_issues;
            }

            public boolean isHas_wiki() {
                return has_wiki;
            }

            public void setHas_wiki(boolean has_wiki) {
                this.has_wiki = has_wiki;
            }

            public boolean isHas_pages() {
                return has_pages;
            }

            public void setHas_pages(boolean has_pages) {
                this.has_pages = has_pages;
            }

            public Object getLicense() {
                return license;
            }

            public void setLicense(Object license) {
                this.license = license;
            }

            public int getOpen_issues_count() {
                return open_issues_count;
            }

            public void setOpen_issues_count(int open_issues_count) {
                this.open_issues_count = open_issues_count;
            }

            public String getDefault_branch() {
                return default_branch;
            }

            public void setDefault_branch(String default_branch) {
                this.default_branch = default_branch;
            }

            public String getNamespace() {
                return namespace;
            }

            public void setNamespace(String namespace) {
                this.namespace = namespace;
            }

            public String getName_with_namespace() {
                return name_with_namespace;
            }

            public void setName_with_namespace(String name_with_namespace) {
                this.name_with_namespace = name_with_namespace;
            }

            public String getPath_with_namespace() {
                return path_with_namespace;
            }

            public void setPath_with_namespace(String path_with_namespace) {
                this.path_with_namespace = path_with_namespace;
            }

            public static class OwnerBean {
                /**
                 * id : 1
                 * login : robot
                 * avatar_url : https://gitee.com/assets/favicon.ico
                 * html_url : https://gitee.com/robot
                 * type : User
                 * site_admin : false
                 * name : robot
                 * email : robot@gitee.com
                 * username : robot
                 * user_name : robot
                 * url : https://gitee.com/robot
                 */

                private int id;
                private String login;
                private String avatar_url;
                private String html_url;
                private String type;
                private boolean site_admin;
                private String name;
                private String email;
                private String username;
                private String user_name;
                private String url;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getLogin() {
                    return login;
                }

                public void setLogin(String login) {
                    this.login = login;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public String getHtml_url() {
                    return html_url;
                }

                public void setHtml_url(String html_url) {
                    this.html_url = html_url;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public boolean isSite_admin() {
                    return site_admin;
                }

                public void setSite_admin(boolean site_admin) {
                    this.site_admin = site_admin;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class SenderBean {
            /**
             * id : 1
             * login : robot
             * avatar_url : https://gitee.com/assets/favicon.ico
             * html_url : https://gitee.com/robot
             * type : User
             * site_admin : false
             * name : robot
             * email : robot@gitee.com
             * username : robot
             * user_name : robot
             * url : https://gitee.com/robot
             */

            private int id;
            private String login;
            private String avatar_url;
            private String html_url;
            private String type;
            private boolean site_admin;
            private String name;
            private String email;
            private String username;
            private String user_name;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public String getHtml_url() {
                return html_url;
            }

            public void setHtml_url(String html_url) {
                this.html_url = html_url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isSite_admin() {
                return site_admin;
            }

            public void setSite_admin(boolean site_admin) {
                this.site_admin = site_admin;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class EnterpriseBean {
            /**
             * name : 开源中国
             * url : https://gitee.com/oschina
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class CommitsBean {
            /**
             * id : 1cdcd819599cbb4099289dbbec762452f006cb40
             * tree_id : db78f3594ec0683f5d857ef731df0d860f14f2b2
             * distinct : true
             * message : Update README.md
             * timestamp : 2018-02-05T23:46:46+08:00
             * url : https://gitee.com/oschina/gitee/commit/1cdcd819599cbb4099289dbbec762452f006cb40
             * author : {"time":"2018-02-05T23:46:46+08:00","name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"}
             * committer : {"name":"robot","email":"robot@gitee.com","username":"robot","user_name":"robot","url":"https://gitee.com/robot"}
             * added : null
             * removed : null
             * modified : ["README.md"]
             */

            private String id;
            private String tree_id;
            private boolean distinct;
            private String message;
            private String timestamp;
            private String url;
            private AuthorBeanX author;
            private CommitterBeanX committer;
            private Object added;
            private Object removed;
            private List<String> modified;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTree_id() {
                return tree_id;
            }

            public void setTree_id(String tree_id) {
                this.tree_id = tree_id;
            }

            public boolean isDistinct() {
                return distinct;
            }

            public void setDistinct(boolean distinct) {
                this.distinct = distinct;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public AuthorBeanX getAuthor() {
                return author;
            }

            public void setAuthor(AuthorBeanX author) {
                this.author = author;
            }

            public CommitterBeanX getCommitter() {
                return committer;
            }

            public void setCommitter(CommitterBeanX committer) {
                this.committer = committer;
            }

            public Object getAdded() {
                return added;
            }

            public void setAdded(Object added) {
                this.added = added;
            }

            public Object getRemoved() {
                return removed;
            }

            public void setRemoved(Object removed) {
                this.removed = removed;
            }

            public List<String> getModified() {
                return modified;
            }

            public void setModified(List<String> modified) {
                this.modified = modified;
            }

            public static class AuthorBeanX {
                /**
                 * time : 2018-02-05T23:46:46+08:00
                 * name : robot
                 * email : robot@gitee.com
                 * username : robot
                 * user_name : robot
                 * url : https://gitee.com/robot
                 */

                private String time;
                private String name;
                private String email;
                private String username;
                private String user_name;
                private String url;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class CommitterBeanX {
                /**
                 * name : robot
                 * email : robot@gitee.com
                 * username : robot
                 * user_name : robot
                 * url : https://gitee.com/robot
                 */

                private String name;
                private String email;
                private String username;
                private String user_name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
