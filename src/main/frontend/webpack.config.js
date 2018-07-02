var path = require('path');

module.exports = {
  entry: {
    client: './src/client.js',
    server: './src/server.js',
  },
  output: {
    path: path.resolve(__dirname, '../../../target/classes/static/js'),
    filename: '[name].bundle.js',
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        include: [
          path.resolve(__dirname, './src')
        ],
        loader: 'babel-loader',
        query: {
          presets: ['es2015', 'react']
        }
      }
    ]
  }
};
