import React, {
	PureComponent,
	PropTypes
} from "react";
import {
	View,
	TouchableWithoutFeedback,
	requireNativeComponent
} from "react-native";

const NativeComponent = requireNativeComponent("YoutubePlayerAndroid", YoutubePlayerAndroid, {});

export class YoutubePlayerAndroid extends PureComponent {
	static propTypes = {
		...View.propTypes,
		...TouchableWithoutFeedback.propTypes,
		text: PropTypes.string
	};

	constructor(props) {
		super(props);
	}

	render() {
		return (
			<TouchableWithoutFeedback {...this.props}>
				<NativeComponent
					style={this.props.style}
					text={this.props.text}
					videoId={this.props.videoId}
					apiKey={this.props.apiKey}
					 />
			</TouchableWithoutFeedback>
		);
	}
}
