import { Platform } from "react-native";

if (Platform.OS !== "android") {
	throw new Error(
		"react-native-youtube-android suppoers android only. "
	);
}

export * from "./YoutubePlayerAndroid.js";