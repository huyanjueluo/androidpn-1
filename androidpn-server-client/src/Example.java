import org.junit.Test;

import com.contorn.androidpn.service.client.AndroidpnServerClient;

public class Example {

	@Test
	public void getUser() {
		String users = AndroidpnServerClient.getInstance().getUser(false, "2012-04-12 15:04:55");
		System.out.println(users);
	}

	@Test
	public void sendSingle() {
		String res = AndroidpnServerClient.getInstance().sendSingle("123", "123",
				"e509a0a4-cea6-3cff-a9b0-0e98a36c5fd4");
		System.out.println(res);
	}

	@Test
	public void sendMultiple() {
		String res = AndroidpnServerClient.getInstance().sendMultiple("333", "444",
				new String[] { "e509a0a4-cea6-3cff-a9b0-0e98a36c5fd4", "b737c188-513e-3546-8952-b74f37136c35" });
		System.out.println(res);
	}

	@Test
	public void sendAll() {
		String res = AndroidpnServerClient.getInstance().sendAll("333", "444");
		System.out.println(res);
	}
}
