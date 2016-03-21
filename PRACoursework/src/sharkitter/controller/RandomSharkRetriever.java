package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Shark;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.Random;

public class RandomSharkRetriever implements Job{
	public RandomSharkRetriever() {}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		Jaws jawsApi = new Jaws("EkZ8ZqX11ozMamO9", "E7gdkwWePBYT75KE", true);
		ArrayList<String> sharkList = jawsApi.getSharkNames();

		Random random = new Random();
		int randomInt = random.nextInt(sharkList.size() + 1);

		String randomSharkName = sharkList.get(randomInt);
		String sharkVideo = jawsApi.getVideo(randomSharkName);
	}
}
