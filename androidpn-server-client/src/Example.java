import org.junit.Test;

import com.contorn.androidpn.service.client.PushManager;

public class Example {

	@Test
	public void getUser() {
		String users = PushManager.getInstance().getUser(false, "2012-04-12 15:04:55");
		System.out.println(users);
	}

	@Test
	public void sendSingle() {
		String res = PushManager.getInstance().sendSingle("123", "123",
				"b737c188-513e-3546-8952-b74f37136c35");
		System.out.println(res);
	}

	@Test
	public void sendMultiple() {
		String res = PushManager.getInstance().sendMultiple("333", "444",
				new String[] { "e509a0a4-cea6-3cff-a9b0-0e98a36c5fd4", "b737c188-513e-3546-8952-b74f37136c35","68d96b1c-1ea8-3117-b00a-5e7a0583f46e" });
		System.out.println(res);
	}

	@Test
	public void sendAll() {
		String res = PushManager.getInstance().sendAll("555", "666");
		System.out.println(res);
	}
}
