import React from 'react';
import { render } from 'react-dom';
import Router from './Router';

const components = {
  Router,
};

window.render = (component, props, container) => {
  if (components[component]) {
    const Component = components[component];
    render(<Component {...props} />, container);
  }
  else {
    throw "No available component: " + component;
  }
};
