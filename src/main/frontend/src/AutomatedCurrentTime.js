import React from 'react';
import Helmet from 'react-helmet';
import Link from './Link';
import { sendGet } from './http';

export default class AutomatedCurrentTime extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      now: props.now
    };
  }

  componentDidMount() {
    this.timer = setInterval(this.tick.bind(this), 1000);
  }

  componentWillUnmount() {
    clearInterval(this.timer);
  }

  tick() {
    sendGet("/ssr/auto", ({ now }) => this.setState({ now }));
  }

  render() {
    return (
      <div>
        <Helmet title="Current time (automated)" />
        <div>
          current time: { this.state.now }
          <hr />
        </div>
        <div>
          <Link href="/ssr">&laquo; Manual</Link>
        </div>
      </div>
    );
  }
}
