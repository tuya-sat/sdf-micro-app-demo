import "./public-path";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import { getI18n } from "react-i18next";
import "./index.css";
import "./lang";
import App from "./App";

export interface IQiankunProps {
  base?: string;
  container?: any;
  onGlobalStateChange?: (
    callback: (
      state: Record<string, any>,
      prevState: Record<string, any>
    ) => void,
    fireImmediately?: boolean
  ) => void;
  setGlobalState?: (state: Record<string, any>) => void;
  mainHistory?: any;
  lang?: string;
  hasPermission?: (permissionCode: string) => boolean;
}

export let setGlobalState: any;
export let micState: any;
export let mainHistory: any;
export let hasPermission: (permissionCode: string) => boolean;

function render(props: IQiankunProps) {
  const { base, container } = props;
  ReactDOM.render(
    <BrowserRouter basename={window.__POWERED_BY_QIANKUN__ ? base : "/"}>
      <App />
    </BrowserRouter>,
    container
      ? container.querySelector("#root")
      : document.querySelector("#root")
  );
}

if (!window.__POWERED_BY_QIANKUN__) {
  render({});
}

export async function bootstrap() {
  console.log("子应用启动");
}

export async function mount(props: IQiankunProps) {
  console.log("挂载子应用", props);
  getI18n().changeLanguage(props.lang);
  if (props.hasPermission) {
    hasPermission = props.hasPermission;
  }
  mainHistory = props.mainHistory;
  render(props);
  if (props.onGlobalStateChange) {
    props.onGlobalStateChange((state, prev) => {
      console.log("资产微应用", state, prev);
      micState = state;
    }, true);
  }
  setGlobalState = (state: Record<string, any>) => {
    if (props.setGlobalState) {
      props.setGlobalState({ ...micState, ...state });
    }
  };
}

export async function unmount(props: IQiankunProps) {
  const { container } = props;
  ReactDOM.unmountComponentAtNode(
    container
      ? container.querySelector("#root")
      : document.querySelector("#root")
  );
}
