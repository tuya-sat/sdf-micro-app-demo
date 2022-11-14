import { Form, Input, Button, Checkbox, Row, Col, message } from "antd";
import api from "src/api";
interface IProps {
  setAppid: (appid: string) => void;
  getList: () => void;
  setspinLoading: React.Dispatch<React.SetStateAction<boolean>>;
}
const Demo = ({ setAppid, getList, setspinLoading }: IProps) => {
  const onFinish = (values: any) => {
    const { username } = values;
    if (!username) {
      message.error("请输入要新增的资产名称");
      return;
    }
    setspinLoading(true);
    api
      .post("/custom-api/v1.0/assets", {
        parent_asset_id: "",
        asset_name: username,
      })
      .then((res) => {
        setAppid(res.result);
        getList();
      })
      .catch((e) => console.log(e))
      .finally(() => setspinLoading(false));
  };
  return (
    <>
      <Row>
        <Col span="10">
          <Form
            name="basic"
            labelCol={{
              span: 8,
            }}
            wrapperCol={{
              span: 16,
            }}
            initialValues={{
              remember: true,
            }}
            onFinish={onFinish}
            autoComplete="off"
          >
            <Form.Item label="新增资产" name="username">
              <Input />
            </Form.Item>

            <Form.Item
              wrapperCol={{
                offset: 8,
                span: 16,
              }}
            >
              <Button type="primary" htmlType="submit">
                新增资产
              </Button>
            </Form.Item>
          </Form>
        </Col>
      </Row>
    </>
  );
};

export default Demo;
