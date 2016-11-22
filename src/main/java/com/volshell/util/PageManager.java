package com.volshell.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.volshell.entity.Category;
import com.volshell.entity.WeChat;
import com.volshell.parser.constant.Constants;

public class PageManager {
	public final static String getBodyByUrl(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody;
		try {
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());

			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						throw new ClientProtocolException(
								"Unexpected response status: " + status);
					}
				}
			};
			responseBody = httpclient.execute(httpget, responseHandler);
		} finally {
			httpclient.close();
		}

		return responseBody;
	}

	public static Category getCategory(Document document) {
		Category category = new Category();
		Element breadcrumbNav = document.getElementsByAttributeValue("class",
				"mianbao").first();
		Elements links = breadcrumbNav.getElementsByTag("a");
		for (Element link : links) {
			String linkHref = link.attr("href");
			String code = URLParser.getTypeCode(linkHref, Constants.CATEGORY);
			if (code != Constants.ERROR) {
				category.setC_id(Integer.parseInt(code));
				category.setC_desc(link.text());
				return category;
			}
		}
		return null;
	}

	/**
	 * 判断所请求链接是否为目标链接
	 * 
	 * @param document
	 * @return
	 */
	public static boolean isTarget(Document document) {
		Element breadcrumbNav = document.getElementsByAttributeValue("class",
				"mianbao").first();
		if (breadcrumbNav == null) {
			return false;
		}
		return true;
	}

	/**
	 * 从页面中获取相关信息，封装成WeChat对象
	 * 
	 * @param document
	 * @return
	 */
	public static WeChat createEntity(Document document)
			throws NullPointerException {
		WeChat weChat = new WeChat();
		Element wechatDesc = document.getElementById("article_extinfo");
		Element w_name = wechatDesc.getElementsByTag("h1").first();
		Element w_code = wechatDesc.getElementsByTag("h5").first();
		Element text_short = w_code.getElementsByTag("p").first();
		Element w_png = null;
		Element text_long = null;
		Element next = document.getElementById("article_extinfo");
		Element keys = next.siblingElements().first();// 获取article_extinfo下面的第一个标签

		// 判断它是否含有a标签
		if (keys.getElementsByTag("a").size() != 0) {// 含有a标签
			w_png = keys.getElementsByTag("img").first();
			text_long = keys;
			if (StringUtil.isBlank(text_long.text())
					|| text_long.text().endsWith("：")) {
				text_long = keys.nextElementSibling().nextElementSibling();
			}
		} else {// 不含有a标签
			w_png = keys.nextElementSibling().getElementsByTag("img").first();
			text_long = keys.nextElementSibling();
			if (StringUtil.isBlank(text_long.text())
					|| keys.text().endsWith("：")) {
				text_long = keys.nextElementSibling().nextElementSibling();
			}
		}

		// 需要从code中截取微信号(code = 微信号+短文案)，有些短文案是不存在的
		String code = w_code.text();
		code = code + " ";
		if (!StringUtil.isBlank(code)) {// 备用微信号
			code = code.substring(0, code.indexOf(" "));
			weChat.setW_code(code);
		} else {
			weChat.setW_code(Constants.DEFAULT_CODE);
		}
		if (!StringUtil.isBlank(text_long.text())) { // 备用长文案
			weChat.setText_long(text_long.text());
		} else {
			weChat.setText_long(Constants.DEFAULT_TEXT);
		}
		if (StringUtil.isBlank(text_short.text())) {// 备用短文案
			weChat.setText_short(text_short.text());
		} else {
			weChat.setText_short(Constants.DEFAULT_TEXT);
		}
		if (w_png != null) {// 备用二维码

			weChat.setW_png(w_png.attr("src"));
		} else {
			w_png = document.getElementById("article").select("img").first();
			if (w_png == null) {
				weChat.setW_png(Constants.DEFAULT_IMG);
			} else {
				weChat.setW_png(w_png.attr("src"));
			}
		}
		if (!StringUtil.isBlank(w_name.text())) {// 备用微信名
			weChat.setW_name(w_name.text());
		} else {
			weChat.setW_name(Constants.DEFAULT_NAME);
		}
		weChat.setCategory(getCategory(document));

		return weChat;
	}

	public static void main(String[] args) {
		String url = "http://www.anyv.net/index.php/viewnews-511";
		String result;
		try {
			result = PageManager.getBodyByUrl(url);
			Document document = Jsoup.parse(result);
			if (!isTarget(document)) {
				System.out.println("request not exists");
				return;
			}
			WeChat weChat = PageManager.createEntity(document);
			if (weChat != null) {
				System.out.println(weChat);
			} else {
				System.out.println("wechat is null");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
