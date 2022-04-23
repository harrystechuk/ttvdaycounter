package net.harrystech.ttvdaycounter;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ttvdaycounter implements ModInitializer {
	static MinecraftClient minecraft;
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("ttvdaycounter");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		minecraft = MinecraftClient.getInstance();
		LOGGER.info("TTVDayCounter has initialized!");
		HttpServer server = null;
		try {
			server = HttpServer.create(new InetSocketAddress(8000), 0);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		server.createContext("/", new MyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();

	}

	static class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			getDay();
			String response = "<head><meta http-equiv=\"refresh\" content=\"5; URL=http://localhost:8000/\"></head><body><h1>Day " + getDay() + "</h1></body>";
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static String getDay() {
		if(minecraft == null || minecraft.player == null || minecraft.player.world == null)
			return null;
		long daytime = minecraft.player.world.getTimeOfDay()+6000;
		int day = (int) daytime / 1000 / 24;
		return String.valueOf(day);
	}
}
