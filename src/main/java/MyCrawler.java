import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/2/26.
 */
public class MyCrawler {

    public static void main(String[] args){
        String url = "http://www.zhihu.com/explore/recommendations";
        // 访问链接并获取页面内容
        String content = Spider.SendGet(url);
        // 使用正则匹配图片的src内容
        ArrayList<ZhiHu> imgSrc = Spider.GetZhihu(content);
        // 打印结果
//        System.out.println(imgSrc);
        for (ZhiHu zhihu : imgSrc) {
            FileReadWriter.writeIntoFile(zhihu.writeString(),
                    "H:/知乎_编辑推荐.txt", true);
        }
        System.out.println("全部抓取完成！");
    }
}
