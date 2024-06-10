package com.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

//유틸리티 클래스를 따로 뺴서 사용
// 필요할때만 사용하게끔

public class Ut {
	// 객체를 JSON 문자열로 변환하기 위한 ObjectMapper 객체
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// 문자열 포맷팅을 통해 문자열 반환
	public static String f(String format, Object... args) {
		return String.format(format, args);
	}

	// 문자열이 null이거나 공백인지 확인
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	// 데이터가 비어있는지 확인하고 비어있을 경우 기본값 반환
	public static <T> T ifEmpty(T data, T defaultValue) {
		if (isEmpty(data)) {
			return defaultValue;
		}

		return data;
	}

	/*
	 * 객체가 비어 있는지 확인하는 메서드. obj 확인할 객체 객체가 비어 있으면 true를 반환하고, 그렇지 않으면 false를 반환합니다.
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof Integer) {
			return (int) obj == 0;
		}

		if (obj instanceof String) {
			return ((String) obj).trim().length() == 0;
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}

		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}

		return false;
	}

	// 자바스크립트를 활용하여 이전 페이지로 이동하는 함수를 생성하는 메서드
	// resultcode 결과 코드
	// msg 메세지
	// 자바스크립트를 포함한 문자열을 반환
	public static String jsHistoryBack(String resultCode, String msg) {
		if (resultCode == null) {
			// 결과 코드가 null이면 빈 문자열로 초기화
			resultCode = "";
		}
		// 메시지가 null이면 빈 문자열로 초기화
		if (msg == null) {
			msg = "";
		}
		// 결과 메시지 생성
		String resultMsg = resultCode + " / " + msg;
		// JavaScript를 사용하여 결과 메시지를 경고창에 표시하고 이전 페이지로 이동하는 코드 반환
		return Ut.f("""
				<script>
					const resultMsg = '%s'.trim();
					if(resultMsg.length > 0){
						alert(resultMsg);
					}
					history.back();
				</script>
				""", resultMsg);
	}

	/*
	 * JavaScript를 사용하여 현재 페이지를 대체하고 새로운 페이지로 이동하는 함수를 생성하는 메서드. resultCode 결과 코드,
	 * msg 메시지 , replaceUri 대체할 URI JavaScript를 포함한 문자열을 반환
	 */
	public static String jsReplace(String resultCode, String msg, String replaceUri) {
		// 결과 코드가 null이면 빈 문자열로 초기화
		if (resultCode == null) {
			resultCode = "";
		}
		// 메시지가 null이면 빈 문자열로 초기화
		if (msg == null) {
			msg = "";
		}
		// 대체 URI가 null이면 "/"로 초기화
		if (replaceUri == null) {
			msg = "/";
		}
		// 결과 메시지 생성
		String resultMsg = resultCode + " / " + msg;
		// JavaScript를 사용하여 결과 메시지를 경고창에 표시하고 대체 URI로 이동하는 코드 반환
		return Ut.f("""
				<script>
					const resultMsg = '%s'.trim();
					if(resultMsg.length > 0){
						alert(resultMsg);
					}
					location.replace('%s');
				</script>
				""", resultMsg, replaceUri);
	}

	/*
	 * 객체를 JSON 문자열로 변환하는 메서드 obj JSON 문자열로 변환할 객체 변환된 JSON 문자열을 반환, 변환 중 예외가 발생하면
	 * null을 반환
	 */
	public static String toJsonString(Object obj) {
		try {
			// ObjectMapper를 사용하여 객체를 JSON 문자열로 변환
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// 변환 중 예외가 발생한 경우 예외를 출력하고 null을 반환
			e.printStackTrace();
			// 실제 프로덕션 환경에서는 로깅 프레임워크를 사용하거나 적절한 예외 처리를 해야 한다
			return null;
		}
	}

	/*
	 * 현재 URI를 UTF-8로 인코딩하는 메서드 currentUri 현재 URI UTF-8로 인코딩된 현재 URI를 반환 인코딩 중 예외가
	 * 발생하면 원래 URI를 반환
	 */
	public static String getEncodedCurrentUri(String currentUri) {

		try {
			// 현재 URI를 UTF-8로 인코딩
			return URLEncoder.encode(currentUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// 인코딩 중 예외가 발생한 경우 예외를 출력하고 원래 URI를 반환
			e.printStackTrace();
			return currentUri;
		}

	}

	/*
	 * 날짜 변환 메서드 seconds 초 단위의 시간 현재 시간 이후로 지정된 초만큼 추가된 날짜 및 시간을
	 * "yyyy-MM-dd HH:mm:ss" 형식으로 반환
	 */
	public static String getDateStrLater(long seconds) {
		// SimpleDateFormat을 사용하여 날짜 및 시간 형식 지정
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 현재 시간에 초를 더한 후 형식에 맞게 문자열로 반환
		String dateStr = format.format(System.currentTimeMillis() + seconds * 1000);
		return dateStr;
	}

	/*
	 * 객체를 JSON 문자열로 변환하는 메서드 obj JSON 문자열로 변환할 객체 변환된 JSON 문자열을 반환 변환 중 예외가 발생한 경우
	 * 빈 문자열을 반환
	 */
	public static String toJsonStr(Object obj) {
		// ObjectMapper 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		try {
			// ObjectMapper를 사용하여 객체를 JSON 문자열로 변환합니다.
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// 변환 중 예외가 발생한 경우 예외를 출력하고 빈 문자열을 반환합니다.
			e.printStackTrace();
		}
		// 변환 중 예외가 발생한 경우 빈 문자열을 반환합니다.
		return "";
	}

	/*
	 * 맵을 JSON 문자열로 변환하는 메서드 param JSON 문자열로 변환할 맵 변환된 JSON 문자열을 반환 변환 중 예외가 발생한 경우
	 * 빈 문자열을 반환
	 */
	public static String toJsonStr(Map<String, Object> param) {
		// ObjectMapper 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(param);
		} catch (JsonProcessingException e) {
			// 변환 중 예외가 발생한 경우 예외를 출력하고 빈 문자열을 반환
			e.printStackTrace();
		}
		// 변환 중 예외가 발생한 경우 빈 문자열을 반환
		return "";
	}

	/*
	 * 맵에서 지정된 속성 이름에 해당하는 값을 가져오는 메서드 만약 맵에 해당 속성 이름이 포함되어 있지 않으면 기본값을 반환 map 값을
	 * 가져올 맵 attrName 가져올 값의 속성 이름 defaultValue 기본값 맵에서 지정된 속성 이름에 해당하는 값이 존재하면 해당
	 * 값, 그렇지 않으면 기본값을 반환
	 */
	public static String getStrAttr(Map map, String attrName, String defaultValue) {
		// 맵에 지정된 속성 이름이 포함되어 있는지 확인하고 값 가져오기
		if (map.containsKey(attrName)) {
			return (String) map.get(attrName);
		}
		// 맵에 해당 속성 이름이 포함되어 있지 않으면 기본값 반환
		return defaultValue;
	}

	/*
	 * 파일 이름에서 확장자를 추출하여 파일 유형 코드를 반환하는 메서드입니다. fileName 파일 이름 파일 유형 코드를 반환 이미지인 경우
	 * "img", 비디오인 경우 "video", 오디오인 경우 "audio", 그 외의 경우 "etc"를 반환
	 */
	public static String getFileExtTypeCodeFromFileName(String fileName) {
		// 파일 이름에서 확장자 추출하여 소문자로 변환
		String ext = getFileExtFromFileName(fileName).toLowerCase();

		// 확장자에 따라 파일 유형 코드 반환
		switch (ext) {
		case "jpeg":
		case "jpg":
		case "gif":
		case "png":
			return "img";
		case "mp4":
		case "avi":
		case "mov":
			return "video";
		case "mp3":
			return "audio";
		}
		// 기본적으로 "etc" 반환
		return "etc";
	}

	/*
	 * 파일 이름에서 확장자를 추출하여 파일 유형 코드를 반환하는 메서드입니다. fileName 파일 이름 파일 유형 코드를 반환 이미지의 경우
	 * "jpg", "gif", "png", 비디오의 경우 "mp4", "mov", "avi", 오디오의 경우 "mp3", 그 외의 경우
	 * "etc"를 반환
	 */
	public static String getFileExtType2CodeFromFileName(String fileName) {
		// 파일 이름에서 확장자 추출하여 소문자로 변환
		String ext = getFileExtFromFileName(fileName).toLowerCase();

		// 확장자에 따라 파일 유형 코드 반환
		switch (ext) {
		case "jpeg":
		case "jpg":
			return "jpg";
		case "gif":
			return ext;
		case "png":
			return ext;
		case "mp4":
			return ext;
		case "mov":
			return ext;
		case "avi":
			return ext;
		case "mp3":
			return ext;
		}
		// 기본적으로 "etc" 반환
		return "etc";
	}

	/*
	 * 파일 이름에서 확장자를 추출하는 메서드 fileName 파일 이름 파일의 확장자를 반환
	 */
	public static String getFileExtFromFileName(String fileName) {
		// 파일 이름에서 마지막 점(.)의 위치를 찾아서 확장자 추출
		int pos = fileName.lastIndexOf(".");
		String ext = fileName.substring(pos + 1);

		return ext;
	}

	/*
	 * 현재 년도와 월을 포함하는 문자열을 반환하는 메서드입니다.
	 * 
	 * @return "yyyy_MM" 형식의 현재 년도와 월을 포함하는 문자열을 반환합니다.
	 */
	public static String getNowYearMonthDateStr() {
		// 현재 시간을 기준으로 "yyyy_MM" 형식의 날짜 문자열 생성
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM");

		String dateStr = format1.format(System.currentTimeMillis());

		return dateStr;
	}

	/*
	 * 문자열을 지정된 구분자로 분할하여 정수 리스트로 반환하는 메서드 str 분할할 문자열 divideBy 구분자 분할된 문자열을 정수로 변환한
	 * 리스트를 반환
	 */
	public static List<Integer> getListDividedBy(String str, String divideBy) {
		// 문자열을 지정된 구분자로 분할하고 각 문자열을 정수로 변환한 후 리스트로 변환하여 반환
		return Arrays.asList(str.split(divideBy)).stream().map(s -> Integer.parseInt(s.trim()))
				.collect(Collectors.toList());
	}

	/*
	 * 지정된 파일을 삭제하는 메서드 filePath 삭제할 파일의 경로 파일이 성공적으로 삭제되면 true를, 파일이 존재하지 않거나 삭제에
	 * 실패하면 false를 반환
	 */
	public static boolean deleteFile(String filePath) {
		// 파일 경로로부터 파일 객체 생성
		java.io.File ioFile = new java.io.File(filePath);
		// 파일이 존재하는 경우 삭제하고 결과 반환, 존재하지 않는 경우 삭제 작업 없이 true 반환
		if (ioFile.exists()) {
			return ioFile.delete();
		}

		return true;
	}

	/*
	 * 지정된 인자를 사용하여 맵을 생성하는 메서드 args 키-값 쌍으로 이루어진 인자 배열. 짝수개의 인자가 필요하며, 홀수 인덱스는 키,
	 * 짝수 인덱스는 값이어야 함 생성된 맵 객체를 반환 IllegalArgumentException 인자의 개수가 홀수인 경우 발생합니다. 키가
	 * String 타입이 아닌 경우에도 발생
	 */
	public static Map<String, Object> mapOf(Object... args) {
		// 인자 개수가 홀수인 경우 예외 발생
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("인자를 짝수개 입력해주세요.");
		}

		int size = args.length / 2;
		// LinkedHashMap을 사용하여 순서를 보장하는 맵 생성
		Map<String, Object> map = new LinkedHashMap<>();
		// 인자 배열을 반복하면서 키-값 쌍을 맵에 추가
		for (int i = 0; i < size; i++) {
			int keyIndex = i * 2;
			int valueIndex = keyIndex + 1;

			String key;
			Object value;
			// 키가 String 타입이 아닌 경우 예외 발생
			try {
				key = (String) args[keyIndex];
			} catch (ClassCastException e) {
				throw new IllegalArgumentException("키는 String으로 입력해야 합니다. " + e.getMessage());
			}

			value = args[valueIndex];

			map.put(key, value);
		}

		return map;
	}

	/*
	 * Object를 int로 변환하여 반환 object 변환할 Object defaultValue 변환 실패 시 반환할 기본값 변환된 int 값
	 * 또는 기본값
	 */
	public static int getAsInt(Object object, int defaultValue) {
		// BigInteger 타입인 경우 int로 변환하여 반환
		if (object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		} // Double 타입인 경우 내림하여 int로 변환하여 반환
		else if (object instanceof Double) {
			return (int) Math.floor((double) object);
		}
		// Float 타입인 경우 내림하여 int로 변환하여 반환
		else if (object instanceof Float) {
			return (int) Math.floor((float) object);
		}
		// Long 타입인 경우 int로 변환하여 반환
		else if (object instanceof Long) {
			return (int) object;
		}
		// Integer 타입인 경우 그대로 반환
		else if (object instanceof Integer) {
			return (int) object;
		}
		// String 타입인 경우 int로 변환하여 반환
		else if (object instanceof String) {
			return Integer.parseInt((String) object);
		}
		// 기본값 반환
		return defaultValue;
	}

	/*
	 * 데이터가 null이면 기본값을 반환 data확인할 데이터
	 * defaultValue 기본값입니다.
	 * 데이터가 null이 아니면 데이터를,
	 * null이면 기본값을 반환
	 */
	public static <T> T ifNull(T data, T defaultValue) {
		return data != null ? data : defaultValue;
	}
	
	/*
	 * HttpServletRequest의 속성을 가져오거나, 기본값을 반환
	 * req  HttpServletRequest 객체
	 * attrName 가져올 속성의 이름
	 * defaultValue 기본값
	 * 속성이 존재하면 해당 속성 값을, 속성이 없거나 null이면 기본값을 반환
	 */
	public static <T> T reqAttr(HttpServletRequest req, String attrName, T defaultValue) {
		return (T) ifNull(req.getAttribute(attrName), defaultValue);
	}


	/*
	 * 비밀번호 암호화
	 * 입력된 문자열에 대한 SHA-256 해시 값을 반환
	 *input 해시할 문자열
	 * 입력된 문자열에 대한 SHA-256 해시 값
	 */
	public static String sha256(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 비밀번호 찾을때 임시 비밀번호 발송
	 * 임시 비밀번호를 생성하여 반환
	 * length 생성할 임시 비밀번호의 길이
	 *  생성된 임시 비밀번호
	 */
	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}

		return sb.toString();
	}
}
