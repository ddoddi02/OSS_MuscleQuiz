import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MusclesImporter {
    public static Map<Integer, Muscle> loadMusclesFromJSON(String filename) {
        Map<Integer, Muscle> muscles = new HashMap<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            // FileReader로 json 파일을 읽어온다.
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            // json 파일을 파싱해서 jsonnObject에 저장

            JSONArray musclesArray = (JSONArray) jsonObject.get("muscles");
            // "muscles" 배열을 musclesArray에 저장

            for (Object obj : musclesArray) {
                JSONObject muscleData = (JSONObject) obj;
                // muscles 배열의 내용들을 하나씩 저장
                
                String numberInString = (String) muscleData.get("number");
                int number = Integer.parseInt(numberInString);
                String OldVersionKorean = (String) muscleData.get("OldVersionKorean");
                String NewVersionKorean = (String) muscleData.get("NewVersionKorean");
                String EnglishAnswer = (String) muscleData.get("EnglishAnswer");
                String image = (String) muscleData.get("image");
                // 각 명령어의 이름, 실행 경로 및 아이콘 경로를 추출

                muscles.put(number, new Muscle(OldVersionKorean, NewVersionKorean, EnglishAnswer, image));
                // 생성자 실행 시 json파일에 있던 커맨드들이 맵에 저장된다.
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return muscles;
    } 
}
