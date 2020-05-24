package per.cz.security.util;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

/**
 * 富文本html 提取纯文本
 * @author Administrator
 * @date 2019/10/9
 */
public class HtmlToText {
    /**
     * 从html中提取纯文本
     * @param strHtml
     * @return
     */
    public static String StripHT(String strHtml) {
        //剔出<html>的标签
        String txtcontent = strHtml.replaceAll("</?[^>]+>", "");
        //去除字符串中的空格,回车,换行符,制表符
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
        return txtcontent;
    }

    /**
     * 正则匹配img图片
     * @param htmlCode 文本
     * @return
     */
    public static List<String> getImageSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        return imageSrcList;
    }

    public static void main(String[] args) {
        String s = "&nbsp;&nbsp;&nbsp;&nbsp;<div label-module=\"para\">&nbsp;&nbsp;&nbsp;&nbsp;《逃避虽可耻但有用》是日本TBS电视台2016年制作播出的爱情喜剧，由<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E5%9C%9F%E4%BA%95%E8%A3%95%E6%B3%B0/664884\">土井裕泰</a>、<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E9%87%91%E5%AD%90%E6%96%87%E7%BA%AA/4847220\">金子文纪</a>、<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E7%9F%B3%E4%BA%95%E5%BA%B7%E6%99%B4/9902217\">石井康晴</a>等执导，<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E9%87%8E%E6%9C%A8%E4%BA%9A%E7%BA%AA%E5%AD%90\">野木亚纪子</a>编剧，新垣结衣主演。于2016年10月11日首播。</div><p><br></p><div label-module=\"para\">&nbsp; &nbsp; &nbsp;该剧改编自海野津美创作的同名漫画，讲述了研究生毕业却找不到工作的森山实栗选择与上班族津崎“契约结婚”，以主妇作为自己职业，二人在共同生活中领悟爱情真谛的故事。<sup>&nbsp;[1]</sup><a name=\"ref_[1]_20578811\">&nbsp;</a></div><div label-module=\"para\"><a name=\"ref_[1]_20578811\"><br></a></div><div label-module=\"para\"><a name=\"ref_[1]_20578811\"><img src=\"http://10.233.1.241:8088/image/5ee5b514-4a96-48d7-87f2-91c4159044b5.jpg\" style=\"max-width:100%;\"><br></a></div><div label-module=\"para\"><a name=\"ref_[1]_20578811\"><img src=\"http://10.233.1.241:8088/image/3f1b2d05-0371-4556-9054-6ee4943b290b.jpg\" style=\"max-width:100%;\"><br></a></div><div label-module=\"para\"><a name=\"ref_[1]_20578811\"><br></a></div><div label-module=\"para\"><a name=\"ref_[1]_20578811\"><br></a></div>";
        List<String> imageSrc = getImageSrc(s);
        System.out.println(imageSrc.get(0).toString());
    }

}
