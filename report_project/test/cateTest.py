class SwitchCase(object):
    def case_to_function(self, case):
        fun_name = "case_fun_" + str(case)
        return getattr(self, fun_name, self.case_fun_other)

    def case_fun_1(self, target_property_array, json_content):
        return 'targetPropertyArray=' + target_property_array + ',jsonContent=' + json_content

    def case_fun_2(self, target_property_array, json_content):
        return 'targetPropertyArray=' + target_property_array + ',jsonContent=' + json_content

    def case_fun_other(self, target_property_array, json_content):
        return 'targetPropertyArray=' + target_property_array + ',jsonContent=' + json_content


if __name__ == "__main__":
    cls = SwitchCase()
    result1 = cls.case_to_function(1)("target_property_array1", 'jsonContent1')
    result2 = cls.case_to_function(2)("target_property_array2", 'jsonContent2')
    result3 = cls.case_to_function(3)("target_property_array3", 'jsonContent3')

    print(result1)
    print(result2)
    print(result3)
