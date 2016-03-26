package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Shark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSharkRetriever {

	private Jaws jawsApi;
	private String randomSharkName;
	private String sharkVideo;


	public RandomSharkRetriever(Jaws jawsApi) {
		this.jawsApi = jawsApi;
	}

	public void retrieveNewShark() {
		List<String> sharkList = jawsApi.getSharkNames();

		Random random = new Random();
		int randomInt = random.nextInt(sharkList.size() + 1);

		randomSharkName = sharkList.get(randomInt);
		sharkVideo = jawsApi.getVideo(randomSharkName);
	}

	public String getSharkName() {
		return randomSharkName;
	}

	public String getSharkVideo() {
		return sharkVideo;
	}
}
