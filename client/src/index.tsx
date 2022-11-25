import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import GlobalStyle from './shared/styled/GlobalStyle';
import { ThemeProvider } from 'styled-components';
import { theme } from './shared/styled/Theme';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement,
);

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 0,   //디버깅용도 추후에 삭제
      suspense: true,
    }
  }
});

root.render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={true}/>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <App />
        </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);
