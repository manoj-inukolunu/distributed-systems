package me.systemdesign.urlshortner;

import com.google.common.base.Stopwatch;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PostGresDataPopulater {

    private final int hunderedMillion = 100000000;

    public String shorten(String url) {
        String hash = Hashing.md5().newHasher().putString(url, Charset.defaultCharset()).hash().toString();
        System.out.println(hash);
        System.out.println(BaseEncoding.base64().encode(hash.getBytes(StandardCharsets.UTF_8)));
        return "";
    }

    public void generate100MillionSequential() throws Exception {
        for (int i = 0; i < 10; i++) {
            File file = new File("/Users/manoj/sequential/10million-" + i + ".csv");
            String url = "https://stackoverflow.com/questions/19167349/";
            FileWriter writer = new FileWriter(file);
            for (int j = 0; j < hunderedMillion; j++) {
                writer.append(url).append(String.valueOf(UUID.randomUUID())).append("\r\n");
            }
            writer.close();
        }
    }

    public void generateBillion() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            list.add(service.submit(() -> {
                try {
                    File file = new File("/Users/manoj/100mil/100million-" + finalI + ".csv");
                    String url = "https://stackoverflow.com/questions/19167349/";
                    FileWriter writer = new FileWriter(file);
                    for (int j = 0; j < hunderedMillion; j++) {
                        writer.append(url).append(String.valueOf(UUID.randomUUID())).append("\r\n");
                    }
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
        }
        for (Future future : list) {
            future.get();
        }
        service.shutdown();
    }

    public void insertToDB() throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "");
        for (File file : Objects.requireNonNull(new File("/Users/manoj/100mil").listFiles())) {
            Statement statement = connection.createStatement();
            String sql = "copy urls (url) from '" + file.getAbsolutePath() + "'";
            System.out.println("Executing sql=" + sql);
            int ret = statement.executeUpdate(sql);
            System.out.printf("Executed with code = %d\n", ret);
            statement.close();
        }
        connection.close();

    }

    public static void main(String args[]) throws Exception {
        PostGresDataPopulater shortner = new PostGresDataPopulater();
        Stopwatch stopwatch = Stopwatch.createStarted();
        shortner.insertToDB();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
    }
}
