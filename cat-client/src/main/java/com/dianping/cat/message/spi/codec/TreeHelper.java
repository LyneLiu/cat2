package com.dianping.cat.message.spi.codec;

import com.dianping.cat.message.Message;
import com.dianping.cat.message.internal.MessageId;
import com.dianping.cat.message.internal.MockMessageBuilder;
import com.dianping.cat.message.spi.internal.DefaultMessageTree;

public class TreeHelper {
	public static DefaultMessageTree tree(final MessageId id) {
		Message message = new MockMessageBuilder() {
			@Override
			public MessageHolder define() {
				TransactionHolder t = t("WEBCLUSTER", "GET",
				      "This&123123&1231&3&\n\n\n\n&\t\t\t\n\n\n\n\n\n is test data\t\t\n\n", 112819) //
				      .at(id.getTimestamp() + 1234000L) //
				      .after(1300).child(t("QUICKIESERVICE", "gimme_stuff", 1571)) //
				      .after(100).child(e("SERVICE", "event1", "This\n\n\n\n\n\n is test data\t\t\n\n")) //
				      .after(100).child(h("SERVICE", "heartbeat1")) //
				      .after(100).child(m("ORDER", "C", "1")) //
				      .after(100).child(t("WEB SERVER", "GET", 109358) //
				            .after(1000).child(t("SOME SERVICE", "get", 4345) //
				                  .after(4000).child(t("MEMCACHED", "Get", 279))) //
				            .mark().after(200).child(t("MEMCACHED", "Inc", 319)) //
				            .reset().after(500).child(t("BIG ASS SERVICE", "getThemDatar", 97155) //
				                  .after(1000).mark().child(t("SERVICE", "getStuff", 3760)) //
				                  .reset().child(t("DATAR", "findThings", 94537)) //
				                  .after(200).child(t("THINGIE", "getMoar", 1435)) //
				            ) //
				            .after(100).mark().child(t("OTHER DATA SERVICE", "get", 4394) //
				                  .after(1000).mark().child(t("MEMCACHED", "Get", 378)) //
				                  .reset().child(t("MEMCACHED", "Get", 3496)) //
				            ) //
				            .reset().child(t("FINAL DATA SERVICE", "get", 4394) //
				                  .after(1000).mark().child(t("MEMCACHED", "Get", 386)) //
				                  .reset().child(t("MEMCACHED", "Get", 322)) //
				                  .reset().child(t("MEMCACHED", "Get", 322)) //
				            ) //
				      ) //
				;

				return t;
			}
		}.build();

		DefaultMessageTree tree = new DefaultMessageTree();

		tree.setDomain(id.getDomain());
		tree.setHostName("mock-host");
		tree.setIpAddress(id.getIpAddress());
		tree.setThreadGroupName("test");
		tree.setThreadId("test");
		tree.setThreadName("test");
		tree.setMessage(message);
		tree.setMessageId(id.toString());
		tree.setParentMessageId("parent");
		tree.setRootMessageId("root");
		tree.setSessionToken("session");

		return tree;
	}

	public static DefaultMessageTree atomicTree(final MessageId id) {
		Message message = new MockMessageBuilder() {
			@Override
			public MessageHolder define() {
				EventHolder e = e("type", "name");

				return e;
			}
		}.build();

		DefaultMessageTree tree = new DefaultMessageTree();

		tree.setDomain(id.getDomain());
		tree.setHostName("mock-host");
		tree.setIpAddress(id.getIpAddress());
		tree.setThreadGroupName("test");
		tree.setThreadId("test");
		tree.setThreadName("test");
		tree.setMessage(message);
		tree.setMessageId(id.toString());
		tree.setParentMessageId("parent");
		tree.setRootMessageId("root");
		tree.setSessionToken("session");

		return tree;
	}

	public static DefaultMessageTree tree(String id) {
		return tree(MessageId.parse(id));
	}

	public static DefaultMessageTree atomicTree(String id) {
		return atomicTree(MessageId.parse(id));
	}
}