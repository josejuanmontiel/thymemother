import React from 'react';

export default class Link extends React.Component {
  constructor(props) {
    super(props);
  }

  handleClick(event) {
    console.log("link.handleclick");
    this.context.onLinkClick(event);
  }

  render() {
    return (
      <a onClick={this.handleClick.bind(this)} {...this.props}>
        {this.props.children}
      </a>
    );
  }
}

Link.contextTypes = {
  onLinkClick: React.PropTypes.func,
};
