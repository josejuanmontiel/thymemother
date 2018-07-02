import React from 'react';
import Helmet from 'react-helmet';
import Link from './Link';
import { sendGet } from './http';

export default class CurrentTime extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      now: props.now
    };
  }

  handleClick(event) {
    event.preventDefault();
    sendGet('/ssr', ({ now }) => this.setState({ now }));
  }

  render() {
    return (
      <div>
        <Helmet title="Current time" />
        <div>
          current time: { this.state.now }
          <button onClick={ this.handleClick.bind(this) }>Update</button>
          <hr />
        </div>
        <div>
          <Link href="/ssr/auto">&raquo; Automated</Link>
        </div>
      </div>
    );
  }
}
