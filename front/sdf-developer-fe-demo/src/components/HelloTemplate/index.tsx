import { Layout } from "antd";
import { Dispatch, SetStateAction } from "react";
import { useTranslation } from "react-i18next";
import logo from "../../../logo.svg";
import Demo from "../Demo";
import styled from "./index.module.less";
const { Sider } = Layout;
const HelloTemplate = function ({
  setAppid,
  getList,
  setspinLoading,
}: {
  setAppid: React.Dispatch<SetStateAction<string>>;
  getList: () => void;
  setspinLoading: React.Dispatch<React.SetStateAction<boolean>>;
}) {
  const { t } = useTranslation();

  return (
    <Layout style={{ height: "200px" }}>
      <Sider className={styled["layout-sider"]} theme={"light"}>
        <img src={logo} alt="logo" />
      </Sider>
      <div className={styled["layout-right"]}>
        <div className={styled["layout-child-left"]}>
          <p>{t("micro.title")}</p>
          <p>
            {t("micro.see")}
            <strong> README.md </strong>
            {t("micro.more")}
          </p>
          <p>{t("micro.mock")}</p>
        </div>
        <div className={styled["layout-child-right"]}>
          <Demo
            getList={getList}
            setAppid={setAppid}
            setspinLoading={setspinLoading}
          />
        </div>
      </div>
    </Layout>
  );
};
export default HelloTemplate;
