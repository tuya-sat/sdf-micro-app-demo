import { getI18n } from "react-i18next";
import { ConfigProvider } from "antd";
import "antd/dist/antd.css";
import "./App.css";
import Page from "./pages";
import zhCN from "antd/lib/locale/zh_CN";
import enUS from "antd/lib/locale/en_US";
import { IQiankunProps } from "src";

function App(props: IQiankunProps) {
  const i18n = getI18n();
  const locale = i18n.language === "zh-CN" ? zhCN : enUS;

  return (
    <ConfigProvider locale={locale}>
      <Page />
    </ConfigProvider>
  );
}

export default App;
