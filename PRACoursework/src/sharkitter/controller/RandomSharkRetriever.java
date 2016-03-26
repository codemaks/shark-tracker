package sharkitter.controller;

import api.jaws.Jaws;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class RandomSharkRetriever {

	private Jaws jawsApi;
	private String randomSharkName;
	private String randomSharkVideo;


	public RandomSharkRetriever(Jaws jawsApi) {
		this.jawsApi = jawsApi;
	}

	private void retrieveNewShark() {
		ArrayList<String> sharkList = jawsApi.getSharkNames();

		Random random = new Random();
		int randomInt = random.nextInt(sharkList.size() + 1);

		randomSharkName = sharkList.get(randomInt);
		randomSharkVideo = jawsApi.getVideo(randomSharkName);
	}

	public void showRandomShark() {
		File f = new File("timestamp.txt");

		//get current date
		Calendar timeNow = Calendar.getInstance();
		String currentDay = (new Integer(timeNow.get(Calendar.DAY_OF_MONTH))).toString();

		String[] infoToWriteToFile = new String[3];
		//first line of text file should be current day
		infoToWriteToFile[0] = currentDay;

		try {
			//if file doesn't exist, create new timestamp.txt file
			if(f.createNewFile()) {
				//get a new random shark
				retrieveNewShark();

				//second line of file should be shark name, third line should be shark video URL
				infoToWriteToFile[1] = randomSharkName;
				infoToWriteToFile[2] = randomSharkVideo;

				//write current day and new shark name/video to file
				BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));

				//write information to file
				for(int i = 0; i < 3; i++) {
					writer.write(infoToWriteToFile[i]);
					writer.newLine();
				}

				writer.close();
			}
			//if file exists
			else {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);

				//read the first line of the file to get the day the application was last loaded
				String dayLastLoaded = br.readLine();

				//if the day has changed, retrieve new shark and write info to file
				if(!dayLastLoaded.equals(currentDay)) {
					//get a new random shark
					retrieveNewShark();

					//store new random shark name and video URL
					infoToWriteToFile[1] = randomSharkName;
					infoToWriteToFile[2] = randomSharkVideo;

					//write current day and new shark name/video to file
					BufferedWriter writer = new BufferedWriter(new FileWriter(f, false));

					for(int i = 0; i < 3; i++) {
						writer.write(infoToWriteToFile[i]);
						writer.newLine();
					}

					writer.close();
				}
				//if the day has not changed, just retrieve shark and video from text file
				else {
					randomSharkName = br.readLine();
					randomSharkVideo = br.readLine();
				}

				br.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSharkName() {
		return randomSharkName;
	}

	public String getSharkVideo() {
		return randomSharkVideo;
	}
}
