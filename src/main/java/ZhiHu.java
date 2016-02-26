import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ZhiHu {
    public String question;// 问题
    public String zhihuUrl;// 网页链接
    public String questionDescription;// 问题描述
    public ArrayList<String> answers;// 存储所有回答的数组
    // 构造方法初始化数据
    public ZhiHu(String Url) {
        question = "";
        zhihuUrl = "";
        questionDescription = "";
        answers = new ArrayList<String>();
        // 判断url是否合法
        if (getRealUrl(Url)) {
            System.out.println("正在抓取" + zhihuUrl);
            // 根据url获取该问答的细节
            String content = Spider.SendGet(zhihuUrl);
            Pattern pattern;
            Matcher matcher;
            // 匹配标题
            pattern = Pattern.compile("zh-question-title.+?<h2.+?>(.+?)</h2>");
            matcher = pattern.matcher(content);
            if (matcher.find()) {
                question = matcher.group(1);
            }
            // 匹配描述
            pattern = Pattern
                    .compile("zh-question-detail.+?<div.+?>(.*?)</div>");
            matcher = pattern.matcher(content);
            if (matcher.find()) {
                questionDescription = matcher.group(1);
            }
            // 匹配答案
            pattern = Pattern.compile("/answer/content.+?<div.+?>(.*?)</div>");
            matcher = pattern.matcher(content);
            boolean isFind = matcher.find();
            while (isFind) {
                answers.add(matcher.group(1));
                isFind = matcher.find();
            }
        }
    }

    // 根据自己的url抓取自己的问题和描述和答案
    public boolean getAll() {
        return true;
    }

    // 处理url
    boolean getRealUrl(String url) {
        // 将http://www.zhihu.com/question/22355264/answer/21102139
        // 转化成http://www.zhihu.com/question/22355264
        // 否则不变
        Pattern pattern = Pattern.compile("question/(.*?)/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            zhihuUrl = "http://www.zhihu.com/question/" + matcher.group(1);
        } else {
            return false;
        }
        return true;
    }

    public String writeString() {
        String result = "";
        result += "问题：" + question + "\r\n";
        result += "描述：" + questionDescription + "\r\n";
        result += "链接：" + zhihuUrl + "\r\n";
        for (int i = 0; i < answers.size(); i++) {
            result += "回答" + i + "：" + answers.get(i) + "\r\n";
        }
        result += "\r\n\r\n";
        // 将其中的html标签进行筛选
        result = result.replaceAll("<br>", "\r\n");
        result = result.replaceAll("<.*?>", "");
        return result;
    }

    //去除答案中的img标签
//    public String deleteImgString(String answer) {
//
//    }

    @Override
    public String toString() {
//        return "问题：" + question + "\n链接：" + zhihuUrl + "\n回答：" + answers + "\n";
        return "问题：" + question + "\n" + "描述：" + questionDescription + "\n"
                + "链接：" + zhihuUrl + "\n回答：" + answers.size() + "\n";
    }
}
