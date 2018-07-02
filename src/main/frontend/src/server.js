import React from 'react';
import { renderToString } from 'react-dom/server';
import Router from './Router';
import Helmet from 'react-helmet';

const components = {
  Router,
};

window.render = (component, props) => {
  if (components[component]) {
    const Component = components[component];
    const html = renderToString(<Component {...props} />);
    window.helmetTitle = Helmet.rewind().title.toString();
    return html;
  }
  else {
    throw "No available component: " + component;
  }
};
