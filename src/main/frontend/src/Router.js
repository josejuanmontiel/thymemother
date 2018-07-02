import React from 'react';
import { sendGet } from './http';
import CurrentTime from './CurrentTime';
import AutomatedCurrentTime from './AutomatedCurrentTime';

const routes = {
  "Root#index": CurrentTime,
  "Auto#index": AutomatedCurrentTime,
};

export default class Router extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      rootProps: props
    };
  }

  componentDidMount() {
    window.addEventListener("popstate", () => {
      this.transitTo(location.href, { pushState: false });
    });
  }

  getChildContext() {
    return {
      onLinkClick: this.handleLinkClick.bind(this)
    };
  }

  handleLinkClick(event) {
    event.preventDefault();
    this.transitTo(event.target.href, { pushState: true });
  }

  transitTo(url, { pushState }) {
    sendGet(url, (rootProps) => {
      if (pushState) history.pushState({}, '', url);
      this.setState({ rootProps });
    });
  }

  route() {
    const { actionPath } = this.state.rootProps;
    const r = routes[actionPath];
    if (!r)
      throw "No routing definition for actionPath: " + actionPath;
    return r;
  }

  render() {
    const Component = this.route();
    return <Component {...this.state.rootProps} />;
  }
}

Router.childContextTypes = {
  onLinkClick: React.PropTypes.func,
};
